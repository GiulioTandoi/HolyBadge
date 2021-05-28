package it.holyfamily.holybadge.structuralservices;

import it.holyfamily.holybadge.database.repositories.InOutParishRepository;
import it.holyfamily.holybadge.database.repositories.ParishionerRepository;
import it.holyfamily.holybadge.entities.InOutParish;
import it.holyfamily.holybadge.entities.Parishioner;
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


    public boolean registerEntrance (int idParishioner, LocalDateTime entranceTime){

        log.info("REGISTRAZIONE DI INGRESSO ALLE " + entranceTime + " PER L'ID " + idParishioner);
        try{
            InOutParish inOutParish = new InOutParish(entranceTime, idParishioner);
            inOutParishRepository.save(inOutParish);
        }catch (Exception ex){
            log.info("ERRORE DURANTE IL SALVATAGGIO DEL MOVIMENTO DI ENTRATA DEL PARISHIONER " + idParishioner);
            ex.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean registerExit(int idParishioner, LocalDateTime exitTime){

        log.info("REGISTRAZIONE DI USCITA ALLE " + exitTime + " PER L'ID " + idParishioner);
        try {

            // Pageable è una classe di spring data che consente di selezionare un range di risultati come un LIMIT
            Pageable lastEntrance = Pageable.ofSize(1);
            InOutParish inOutParish = inOutParishRepository.findByIdParishionerOrderByEntranceTimeDesc(idParishioner, lastEntrance).get(0);
            inOutParish.setExitTime(exitTime);
            inOutParishRepository.save(inOutParish);

        }catch (Exception ex){
            log.info("ERRORE DURANTE IL SALVATAGGIO DEL MOVIMENTO DI USCITA DEL PARISHIONER " + idParishioner);
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public List<HashMap<String , Object>> getInOutMovements(){

        try {

            Pageable lastTwentyMovements = Pageable.ofSize(20);
            List<InOutParish> inOutParishMovements = inOutParishRepository.findAllByOrderByEntranceTimeDesc(lastTwentyMovements);
            List<HashMap <String, Object>> allMovements = new ArrayList<>();
            HashMap <String, Object> singleMovement = new HashMap<>();
            for (InOutParish inOutParish: inOutParishMovements) {

                Optional<Parishioner> parishioner = parishionerRepository.findById(inOutParish.getIdParishioner());
                if (parishioner.isPresent()){

                    singleMovement.put("idParishioner", inOutParish.getIdParishioner());
                    singleMovement.put("entranceTime", inOutParish.getEntranceTime());
                    singleMovement.put("exitTime", inOutParish.getExitTime());
                    singleMovement.put("name", parishioner.get().getName());
                    singleMovement.put("surname", parishioner.get().getSurname());
                    allMovements.add(singleMovement);
                }else {
                    return null;
                }

            }
            return allMovements;
        }catch (Exception ex){
            log.info("Errore durante recupero della lista degli ultimi 20 movmenti di ingresso uscita dalla parrocchia");
            return null;
        }
    }

    public List<Parishioner> getParishionersList(){

        try {

            return (List<Parishioner>) parishionerRepository.findAll();

        }catch (Exception ex){
            log.info("Errore durante recupero della lista degli ultimi 20 movmenti di ingresso uscita dalla parrocchia");
            ex.printStackTrace();
            return null;
        }
    }

    public HashMap<String, Object> getParishionerDetails (int idParishioner){

        HashMap<String, Object> parishionerDetails = null;
        try{


        }catch (Exception ex){
            log.error("ERRORE DURANTE IL RECUPERO DEL DETTAGLIO DEL PARROCCHIANO " + idParishioner, ex);
        }

        return parishionerDetails;

    }

}
