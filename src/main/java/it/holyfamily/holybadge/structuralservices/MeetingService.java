package it.holyfamily.holybadge.structuralservices;

import it.holyfamily.holybadge.database.repositories.MeetingRepository;
import it.holyfamily.holybadge.database.repositories.ParishionerRepository;
import it.holyfamily.holybadge.database.repositories.PartecipationRepository;
import it.holyfamily.holybadge.entities.Meeting;
import it.holyfamily.holybadge.entities.Parishioner;
import it.holyfamily.holybadge.entities.Partecipation;
import it.holyfamily.holybadge.pojos.MeetingPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {

    @Qualifier("it.holyfamily.holybadge.database.repositories.MeetingRepository")
    @Autowired
    MeetingRepository meetingRepository;

    @Qualifier("it.holyfamily.holybadge.database.repositories.PartecipationRepository")
    @Autowired
    PartecipationRepository partecipationRepository;

    @Qualifier("it.holyfamily.holybadge.database.repositories.ParishionerRepository")
    @Autowired
    ParishionerRepository parishionerRepository;

    private static final Logger logger = Logger.getLogger(MeetingService.class);

    public List<Meeting> getMeetingsList() {

        List<Meeting> meetings = null;
        //Pageable firstElements = Pageable.ofSize(numElements);
        try {
//            meetings = meetingRepository.findAllByOrderByDateDesc(firstElements);
            meetings = meetingRepository.findAllByOrderByDateDescGreaterThen(LocalDateTime.now());
        } catch (Exception ex) {
            logger.error("ERRORE DURANTE IL RECUPERO LA LISTA DI INCONTRI", ex);
        }

        return meetings;
    }

    public List<Parishioner> getMeetingPartecipants(int idMeeting) {

        List<Parishioner> partecipants = null;

        try {

            partecipants = parishionerRepository.getAllMeetingPartecipants(idMeeting);

        } catch (Exception ex) {
            logger.error("ERRORE DURANTE IL RECUPERO DEI PARTECIPANTI ALL'INCONTRO " + idMeeting, ex);
        }

        return partecipants;
    }

    public Meeting createMeeting(MeetingPojo meeting) {

        Meeting meetingCreated = new Meeting(meeting);
        try {
            meetingCreated = meetingRepository.save(meetingCreated);
        } catch (Exception ex) {
            logger.error("ERRORE DURANTE LA CREAZIONE DEL MEETING " + meeting.getMeetingName(), ex);
        }

        return meetingCreated;
    }

    public boolean deleteMeeting(int meetingId) {

        try {
            Optional<Meeting> meeting = meetingRepository.findById(meetingId);
            if (meeting.isPresent()) {
                meetingRepository.delete(meeting.get());
                return true;
            } else {
                logger.info("IL GRUPPO DA CANCELLARE CON ID " + meetingId + " NON E' PRESENTE SUL DB");
                return false;
            }

        } catch (Exception ex) {
            logger.error("ERRORE DURANTE LA RIMOZIONE DEL GRUPPO " + meetingId, ex);
            return false;
        }

    }

    public boolean addGroupToMeeting(String groupName, int idMeeting) {

        try {
            List<Parishioner> parishionersOfGroup = parishionerRepository.getAllGroupMembers(groupName);
            List<Partecipation> partecipations = null;
            Partecipation partecipation;
            for (Parishioner parishioner : parishionersOfGroup) {
                partecipation = new Partecipation();
                partecipation.setIdMeeting(idMeeting);
                partecipation.setIdParishioner(parishioner.getId());
                partecipations.add(partecipation);
            }

            return partecipationRepository.saveAll(partecipations) != null;
        } catch (Exception ex) {
            logger.error("ERRORE DURANTE L'AGGIUNTA DEL GRUPPO " + groupName + " al meeting " + idMeeting, ex);
            return false;
        }

    }

    public boolean addSingleParishionerToMeeting(int idParishioner, int idMeeting) {

        try {

            Partecipation partecipation = new Partecipation();
            partecipation.setIdParishioner(idParishioner);
            partecipation.setIdMeeting(idMeeting);
            return partecipationRepository.save(partecipation) != null;

        } catch (Exception ex) {
            logger.error("ERRORE DURANTE L'AGGIUNTA DEL PARROCCHIANO " + idParishioner + " al meeting " + idMeeting, ex);
            return false;
        }

    }

    public boolean removeParishionerFromMeeting(int idParishioner, int idMeeting) {

        try {

            partecipationRepository.delete(partecipationRepository.findByIdParishionerAndIdMeeting(idParishioner, idMeeting));
            return true;

        } catch (Exception ex) {
            logger.error("ERRORE DURANTE LA RIMOZIONE DEL PARROCCHIANO " + idParishioner + " dal meeting " + idMeeting, ex);
            return false;
        }

    }

    public boolean removeGroupFromMeeting(int idGroup, int idMeeting) {

        try {

            List<Partecipation> partecipationsToBeDeleted = null;
            List<Parishioner> parishionersOfGroup = parishionerRepository.getAllGroupMembers(idGroup);

            for (Parishioner parishioner : parishionersOfGroup) {
                partecipationsToBeDeleted.add(partecipationRepository.findByIdParishionerAndIdMeeting(parishioner.getId(), idMeeting));
            }

            partecipationRepository.deleteAll(partecipationsToBeDeleted);
            return true;

        } catch (Exception ex) {
            logger.error("ERRORE DURANTE LA RIMOZIONE " + idGroup + " dal meeting " + idMeeting, ex);
            return false;
        }

    }

    public Meeting modifyMeeting(Meeting meeting) {

        try {
            return meetingRepository.save(meeting);
        } catch (Exception ex) {
            logger.error("ERRORE DURANTE L'UPDATE DELL'INCONTRO " + meeting.getId(), ex);
            return null;
        }

    }

}
