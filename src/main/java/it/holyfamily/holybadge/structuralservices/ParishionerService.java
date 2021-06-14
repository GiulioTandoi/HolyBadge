package it.holyfamily.holybadge.structuralservices;

import it.holyfamily.holybadge.database.repositories.*;
import it.holyfamily.holybadge.entities.*;
import it.holyfamily.holybadge.pojos.ParishionerAdditionalInfoPojo;
import it.holyfamily.holybadge.pojos.ParishionerPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ParishionerService {

    private static final Logger log = Logger.getLogger(ParishionerService.class.getName());

    @Autowired
    @Qualifier("it.holyfamily.holybadge.database.repositories.InOutParishRepository")
    private InOutParishRepository inOutParishRepository;

    @Autowired
    @Qualifier("it.holyfamily.holybadge.database.repositories.ParishionerRepository")
    private ParishionerRepository parishionerRepository;

    @Autowired
    @Qualifier("it.holyfamily.holybadge.database.repositories.PartecipationRepository")
    private PartecipationRepository partecipationRepository;

    @Autowired
    @Qualifier("it.holyfamily.holybadge.database.repositories.MembershipRepository")
    private MembershipRepository membershipRepository;

    @Autowired
    @Qualifier("it.holyfamily.holybadge.database.repositories.ParishionerAdditionalInfoRepository")
    private ParishionerAdditionalInfoRepository parishionerAdditionalInfoRepository;

    @Autowired
    @Qualifier("it.holyfamily.holybadge.database.repositories.ParishionerToAdditionalRepository")
    private ParishionerToAdditionalRepository parishionerToAdditionalRepository;

    public boolean registerEntrance(int idParishioner, LocalDateTime entranceTime) {

        log.info("REGISTRAZIONE DI INGRESSO ALLE " + entranceTime + " PER L'ID " + idParishioner);
        try {
            List<InOutParish> lastEntrances = inOutParishRepository.findByIdParishionerAndExitTimeNullOrderByEntranceTimeDesc(idParishioner, Pageable.ofSize(1));
            if (!lastEntrances.isEmpty()){
                inOutParishRepository.save(lastEntrances.get(0));
            }else{
                InOutParish inOutParish = new InOutParish(entranceTime, idParishioner);
                inOutParishRepository.save(inOutParish);
            }

        } catch (Exception ex) {
            log.error("ERRORE DURANTE IL SALVATAGGIO DEL MOVIMENTO DI ENTRATA DEL PARISHIONER " + idParishioner, ex);
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public String registerExit(int idParishioner, LocalDateTime exitTime) {

        log.info("REGISTRAZIONE DI USCITA ALLE " + exitTime + " PER L'ID " + idParishioner);
        try {

            // Pageable è una classe di spring data che consente di selezionare un range di risultati come un LIMIT
            Pageable lastEntrance = Pageable.ofSize(1);
            InOutParish inOutParish = inOutParishRepository.findByIdParishionerAndExitTimeNullOrderByEntranceTimeDesc(idParishioner, lastEntrance).get(0);

            if (inOutParish != null && inOutParish.getEntranceTime() != null){
                // SETTO LA PARTECIPAZIONE DEL PARISHIONER A TUTTI GLI INCONTRI CHE AVEVA IN PROGRAMMA PER L'ORARIO IN CUI E' STATO IN PARROCCHIA
                // recupero tutti gli incontri che aveva in programma il parishioner per la data odierna CON L'ORARIO DI INIZIO PRECEDENTE ALL'ORARIO DI USCITA dalla parrocchia
                try {
                    List<Integer> scheduledMeetingsId = partecipationRepository.getAllMeetingsIdBeforeExit(idParishioner, exitTime, inOutParish.getEntranceTime());
                    Partecipation partecipation;
                    for (int idMeeting : scheduledMeetingsId) {
                        partecipation = partecipationRepository.findByIdParishionerAndIdMeeting(idParishioner, idMeeting);
                        partecipation.setPartecipated(inOutParish.getEntranceTime());
                        partecipationRepository.save(partecipation);
                    }
                    log.info("PARTECIPAZIONI A CUI IL PARROCCHIANO HA PARTECIPATO PRIMA DI USCIRE " + scheduledMeetingsId.size());
                } catch (Exception ex) {
                    log.error("ERRORE DURANTE IL SETTAGIO DELLE PARTECIPAZIONI DEL PARROCCHIANO", ex);
                }

                inOutParish.setExitTime(exitTime);
                inOutParishRepository.save(inOutParish);
            }else {
                log.info("IL PARROCCHIANO " + idParishioner + " NON E' ENTRATO IN PARROCCHIA");
                return "IL PARROCCHIANO NON E' ENTRATO IN PARROCCHIA";
            }

        } catch (Exception ex) {
            log.error("ERRORE DURANTE IL SALVATAGGIO DEL MOVIMENTO DI USCITA DEL PARISHIONER " + idParishioner, ex);
            ex.printStackTrace();
            return "ERRORE DURANTE IL SALVATAGGIO DEL MOVIMENTO DI USCITA DEL PARISHIONER ";
        }

        return "REGISTRAZIONE MOVIMENTO EFFETTUATA";
    }

    public boolean registerPartecipationToMeeting(int idParishioner, int idMeeting, LocalDateTime partecipationDate) {

        log.info("REGISTRAZIONE DI PARTECIPAZIONE ALLE " + partecipationDate + " PER L'ID " + idParishioner);
        try {

            // Pageable è una classe di spring data che consente di selezionare un range di risultati come un LIMIT
            Pageable firstOne = Pageable.ofSize(1);
            Partecipation partecipation = partecipationRepository.findByIdParishionerAndIdMeetingAndPartecipatedNull(idParishioner, idMeeting, firstOne).get(0);
            partecipation.setPartecipated(partecipationDate);
            partecipationRepository.save(partecipation);

        } catch (Exception ex) {
            log.error("ERRORE DURANTE IL SALVATAGGIO DEL MOVIMENTO DI USCITA DEL PARISHIONER " + idParishioner, ex);
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public List<HashMap<String, Object>> getInOutMovements() {

        try {

            Pageable lastHundredMovements = Pageable.ofSize(100);
            List<InOutParish> inOutParishMovements = inOutParishRepository.findAllByOrderByEntranceTimeDesc(lastHundredMovements);
            List<HashMap<String, Object>> allMovements = new ArrayList<>();
            HashMap<String, Object> singleMovement;
            for (InOutParish inOutParish : inOutParishMovements) {
                singleMovement = new HashMap<>();
                Optional<Parishioner> parishioner = parishionerRepository.findById(inOutParish.getIdParishioner());
                log.info("Id parishioner " + inOutParish.getIdParishioner() + " e parishioner recuperato " + parishioner);
                if (parishioner.isPresent()) {

                    singleMovement.put("idParishioner", inOutParish.getIdParishioner());
                    singleMovement.put("entranceTime", inOutParish.getEntranceTime());
                    singleMovement.put("exitTime", inOutParish.getExitTime());
                    singleMovement.put("name", parishioner.get().getName());
                    singleMovement.put("surname", parishioner.get().getSurname());
                    allMovements.add(singleMovement);
                } else {
                    throw new Exception(String.valueOf(parishioner.get()));
                }

            }
            return allMovements;
        } catch (Exception ex) {
            log.error("Errore durante recupero della lista degli ultimi 100 movmenti di ingresso uscita dalla parrocchia", ex);
            return null;
        }
    }

    public List<Parishioner> getParishionersList() {

        try {

            return (List<Parishioner>) parishionerRepository.findAll();

        } catch (Exception ex) {
            log.error("Errore durante recupero della lista degli ultimi 20 movmenti di ingresso uscita dalla parrocchia", ex);
            ex.printStackTrace();
            return null;
        }
    }

    public Parishioner getParishionerBaseInfo(int idParishioner) {

        try {
            return parishionerRepository.findById(idParishioner).get();
        } catch (Exception ex) {
            log.error("ERRORE DURANTE IL RECUPERO DEL DETTAGLIO DEL PARROCCHIANO " + idParishioner, ex);
            return null;
        }

    }

    public List<ParishionerAdditionalInfo> getParishionerAdditionalInfo(int idParishioner) {

        List<ParishionerAdditionalInfo> additionalInfo = null;
        try {
            additionalInfo = parishionerAdditionalInfoRepository.getParishionerAdditionalInfo(idParishioner);
        } catch (Exception ex) {
            log.error("ERRORE DURANTE IL RECUPERO DELLE INFO AGGIUNTIVE DEL PARROCCHIANO " + idParishioner, ex);
        }
        return additionalInfo;
    }

    public ParishionerAdditionalInfo addAdditionalInfoToAll(ParishionerAdditionalInfoPojo parishionerAdditionalInfoPojo) {

        ParishionerAdditionalInfo parishionerAdditionalInfo = new ParishionerAdditionalInfo(parishionerAdditionalInfoPojo);
        try {

            List<Parishioner> allParishioners = new ArrayList<>();
            try {
                parishionerAdditionalInfo = parishionerAdditionalInfoRepository.save(parishionerAdditionalInfo);
                allParishioners = (List<Parishioner>) parishionerRepository.findAll();
                log.info("INFORMAZIONE AGGIUNTIVA CREATA, MAPPO INFOMRAZIONE A TUTTI I PARROCCHIANI...");
            } catch (Exception ex) {
                log.error("ERRORE DURANTE LA CREAZIONE DELLA NUOVA INFOMRAZIONE AGGIUNTIVA O DEL RECUPERO DI TUTTI I PARROCCHIANI", ex);
            }

            ParishionerToAdditional relation;
            for (Parishioner parishioner : allParishioners) {
                relation = new ParishionerToAdditional();
                relation.setIdParishioner(parishioner.getId());
                relation.setIdInfo(parishionerAdditionalInfo.getId());
                parishionerToAdditionalRepository.save(relation);
            }

            log.info("OPERAZIONE DI MAPPING DELL'INFORMAZIONE AGGIUNTIVA " + parishionerAdditionalInfoPojo.getInfoName() + " COMPLETATA PER TUTTI I PARROCCHIANI!");


        } catch (Exception ex) {
            log.error("ERRORE DURANTE L'ASSOCIAZIONE DELLA INFO AGGIUNTIVA " + parishionerAdditionalInfo.getInfoName() + " A TUTTI I PARROCCHIANI", ex);
            return null;
        }
        return parishionerAdditionalInfo;
    }

    public HashMap<String, Object> addAdditionalInfoToSingleParishioner(ParishionerAdditionalInfoPojo parishionerAdditionalInfoPojo, int idParishioner) {

        HashMap<String, Object> completeParishioner = new HashMap<>();
        Parishioner parishioner = new Parishioner();
        ParishionerAdditionalInfo parishionerAdditionalInfo = new ParishionerAdditionalInfo();

        try {

            try {
                parishionerAdditionalInfo = new ParishionerAdditionalInfo(parishionerAdditionalInfoPojo);
                parishionerAdditionalInfo = parishionerAdditionalInfoRepository.save(parishionerAdditionalInfo);
                parishioner = parishionerRepository.findById(idParishioner).get();
                log.info("INFORMAZIONE AGGIUNTIVA CREATA, MAPPO INFOMRAZIONE AL PARROCCHIANO " + idParishioner);
            } catch (Exception ex) {
                log.error("ERRORE DURANTE LA CREAZIONE DELLA NUOVA INFOMRAZIONE AGGIUNTIVA O DEL RECUPERO DI TUTTI I PARROCCHIANI", ex);
            }

            ParishionerToAdditional relation = new ParishionerToAdditional();
            relation.setIdParishioner(parishioner.getId());
            relation.setIdInfo(parishionerAdditionalInfo.getId());
            parishionerToAdditionalRepository.save(relation);

            completeParishioner.put("parishioner", parishioner);
            completeParishioner.put("additionalInformations", getParishionerAdditionalInfo(idParishioner));
            log.info("OPERAZIONE DI MAPPING DELL'INFORMAZIONE AGGIUNTIVA " + parishionerAdditionalInfoPojo.getInfoName() + " COMPLETATA PER IL PARROSHHIANO!");


        } catch (Exception ex) {
            log.error("ERRORE DURANTE L'ASSOCIAZIONE DELLA INFO AGGIUNTIVA " + parishionerAdditionalInfo.getInfoName() + " AL PARROCCHIANO " + idParishioner, ex);
            return null;
        }
        return completeParishioner;
    }

    public Parishioner createParishioner(ParishionerPojo parishionerPojo) {

        Parishioner parishionerCreated = new Parishioner(parishionerPojo);
        try {
            parishionerCreated = parishionerRepository.save(parishionerCreated);
        } catch (Exception ex) {
            log.error("ERRORE DURANTE LA CREAZIONE DEL PARROCCHIANO " + parishionerPojo.getName(), ex);
        }

        try{
            Partecipation partecipation;
            for (int idMeeting : parishionerPojo.getPartecipations()){
                partecipation = new Partecipation();
                partecipation.setIdParishioner(parishionerCreated.getId());
                partecipation.setIdMeeting(idMeeting);
                partecipationRepository.save(partecipation);
            }

            Membership membership;
            for (int idGroup : parishionerPojo.getMemberships()){
                membership = new Membership();
                membership.setIdGroup(idGroup);
                membership.setIdParishioner(parishionerCreated.getId());
                membershipRepository.save(membership);
            }
        }catch(Exception ex){
            log.error("ERRORE DURANTE L?ASSOCIAZIONE DEL NUOVO PARROCCHIANO AI GRUPPI O AI MEETING", ex);
        }

        return parishionerCreated;
    }

    public boolean removeParishionerAdditionalInfo(int idParishioner, int idAdditionalInfo) {

        try {
            parishionerToAdditionalRepository.delete(parishionerToAdditionalRepository.findByIdParishionerAndIdInfo(idParishioner, idAdditionalInfo));
            return true;

        } catch (Exception ex) {
            log.error("ERRORE DURANTE LA RIMOZIONE DELLA INFO AGGIUNTIVA " + idAdditionalInfo + " DAL PARROCHIANO " + idParishioner, ex);
            return false;
        }

    }

    public boolean removeParishioner(int idParishioner) {
        return true;

    }

    public Parishioner modifyParishioner(Parishioner parishioner) {

        try {
            return parishionerRepository.save(parishioner);
        } catch (Exception ex) {
            log.error("ERRORE DURANTE L'UPDATE DEL PARROCCHIANO " + parishioner.getId(), ex);
            return null;
        }

    }

}
