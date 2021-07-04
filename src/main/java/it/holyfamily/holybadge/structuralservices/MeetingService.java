package it.holyfamily.holybadge.structuralservices;

import it.holyfamily.holybadge.database.repositories.*;

import it.holyfamily.holybadge.entities.*;
import it.holyfamily.holybadge.pojos.MeetingPojo;
import it.holyfamily.holybadge.pojos.MembershipPojo;
import it.holyfamily.holybadge.pojos.PartecipantPojo;
import it.holyfamily.holybadge.pojos.PartecipationPojo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Qualifier("it.holyfamily.holybadge.database.repositories.GroupRepository")
    @Autowired
    GroupRepository groupRepository;

    @Qualifier("it.holyfamily.holybadge.database.repositories.MembershipRepository")
    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    GroupService groupsService;

    private static final Logger logger = Logger.getLogger(MeetingService.class);

    public Meeting getMeetingDetails(int idMeeting){
        try{
            return meetingRepository.findById(idMeeting).get();
        }catch (Exception ex ){
            logger.error("ERRORE DURANTE IL RECUPERO DEI DETTAGLI DELL'INCONTRO", ex);
        }
        return null;
    }

    public List<Meeting> getMeetingsList() {

        List<Meeting> meetings = null;
        //Pageable firstElements = Pageable.ofSize(numElements);
        try {
//            meetings = meetingRepository.findAllByOrderByDateDesc(firstElements);
            meetings = meetingRepository.findAllByOrderByDateDesc();
        } catch (Exception ex) {
            logger.error("ERRORE DURANTE IL RECUPERO LA LISTA DI INCONTRI", ex);
        }

        return meetings;
    }

    public List<PartecipantPojo> getMeetingPartecipants(int idMeeting) {

        List<PartecipantPojo> partecipants = new ArrayList<>();

        try {

            List <Parishioner> parishionersList = parishionerRepository.getAllMeetingPartecipants(idMeeting);

            Partecipation partecipation;
            PartecipantPojo partecipantPojo;
            for (Parishioner parishioner: parishionersList){
            	logger.info("RECUPERO PARTECIPANTE");
            	logger.info("ID PARISHIONER " + parishioner.getId() + " ID MEETING " + idMeeting);
                partecipation = partecipationRepository.findByIdParishionerAndIdMeeting(parishioner.getId(), idMeeting);
                partecipantPojo = new PartecipantPojo(parishioner, idMeeting, partecipation.getPartecipated());
                partecipants.add(partecipantPojo);
            }
            
            logger.info("LUNGHEZZA PARTECIPANTI " + partecipants.size() + " : " + partecipants.toString());

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
            System.out.println("Parrocchiani del gruppo " + groupName + " : " + parishionersOfGroup.toString());
            for (Parishioner parishioner : parishionersOfGroup) {
        	   addSingleParishionerToMeeting(parishioner.getId(), idMeeting);
            }
            return true;
        } catch (Exception ex) {
            logger.error("ERRORE DURANTE L'AGGIUNTA DEL GRUPPO " + groupName + " al meeting " + idMeeting, ex);
            return false;
        }

    }

    public List <PartecipationPojo> getMeetingsOfParishioner(int idParishioner){

        try {

            List <PartecipationPojo> partecipationsList = new ArrayList<>();
            List <Meeting> parishionerPartecipations = meetingRepository.getAllParishionerPartecipations(idParishioner);
            List <Meeting> all = (List<Meeting>) meetingRepository.findAll();
            PartecipationPojo partecipationPojo;
            for (Meeting meeting : all){
                partecipationPojo = new PartecipationPojo();
                if (parishionerPartecipations.contains(meeting)){

                    partecipationPojo.setPartecipation(partecipationRepository.findByIdParishionerAndIdMeeting(idParishioner, meeting.getId()).getPartecipated());
                    partecipationPojo.setMeetingName(meeting.getMeetingName());
                    partecipationPojo.setMeetingDate(meeting.getDate());
                    partecipationPojo.setMeetingLocation(meeting.getLocation());
                    partecipationPojo.setIdMeeting(meeting.getId());
                    partecipationsList.add(partecipationPojo);

                }

            }

            return partecipationsList;

        }catch (Exception ex){
            logger.error("ERRORE DURANTE IL RECUPERO DEGLI INCONTRI DEL PARROCCHIANO " + idParishioner, ex);
            return null;
        }

    }

    public List<MembershipPojo> getMembershipsOfPartecipant (int idParishioner){

        try{

            List<MembershipPojo> completedList = new ArrayList<>();
            List<Group> all = groupRepository.findAll();
            List<Group> memberships = groupRepository.getGroupNamesOfParishioner(idParishioner);
            for(Group g: all){
                if (memberships.contains(g)){
                    logger.info("Membership contiene " + g.getName());
                    completedList.add(new MembershipPojo(g, true));
                }else{
                    completedList.add(new MembershipPojo(g, false));
                }

            }

            return completedList;

        }catch (Exception ex){
            logger.error("ERRORE DURANTE IL RECUPERO DEI GRUPPI DI APPARTENENZA DEL PARROCCHIANO " + idParishioner,ex);
            return null;
        }

    }

    public List<PartecipantPojo> getAllNotPartecipants (int idMeeting){

        List<PartecipantPojo> notPartecipants = new ArrayList<>();
        try {

            List <Parishioner> allParishioners = (List<Parishioner>) parishionerRepository.findAll();
            logger.info("id Meeting " + idMeeting);
            List <Integer> pars = partecipationRepository.getIdParishionersByIdMeeting(idMeeting);
            logger.info("Partecipanti " + pars.toString());
            for (Parishioner parishioner : allParishioners){
            	
            	logger.info("Controllo id parishioner " + parishioner.getId());
                if (!pars.contains(parishioner.getId())){
                    notPartecipants.add(new PartecipantPojo(parishioner, idMeeting, null));
                }

            }
            
            
            logger.info("Non partecipanti " + notPartecipants);
            return notPartecipants;
        }catch (Exception ex){
            logger.error("ERRORE DURANTE IL RECUPERO DEI NON PARTECIPANTI ALL'INCONTRO " + idMeeting, ex);
            return null;
        }

    }

    public List<Group> getAllGroupNotAdded(int idMeeting){
        try {

            List<Group> notAdded = new ArrayList<>();
            List <Group> allGroups = groupsService.getGroupsList();
            List <Integer> pars = partecipationRepository.getIdParishionersByIdMeeting(idMeeting);
            List <Integer> allMembers;
            for (Group group : allGroups){

                allMembers = membershipRepository.findAllMembersIdByIdGroup(group.getId());
                logger.info("All Members " + allMembers.toString());
                if (!pars.containsAll(allMembers)){
                    notAdded.add(group);
                }

            }

            return notAdded;
        }catch (Exception ex){
            logger.error("ERRORE DURANTE IL RECUPERO DEI GRUPPI NON AGGIUNTI ALL'INCONTRO " + idMeeting, ex);
            return null;
        }
    }

    public List <Meeting> getParishionerPossibleMeetings(int idParishioner){
        List <Meeting> possibleMeetings = null;
        try{
            possibleMeetings = meetingRepository.getAllPossibleMeetingsForParishioner(idParishioner);
        }catch(Exception ex){
            logger.error("ERRORE DURANTE IL RECUPERO DEI MEETING DISPONIBILI PER IL PARROCCHIANO " + idParishioner, ex);
        }
        return possibleMeetings;

        
    }

    public boolean addSingleParishionerToMeeting(int idParishioner, int idMeeting) {

        Partecipation partecipation;
        try {
            partecipation = new Partecipation();
            partecipation.setIdParishioner(idParishioner);
            partecipation.setIdMeeting(idMeeting);
            if (partecipationRepository.findByIdParishionerAndIdMeeting(idParishioner, idMeeting) == null){
                partecipationRepository.save(partecipation);
            }


        }catch (Exception ex) {
            logger.error("ERRORE DURANTE L'AGGIUNTA DEL PARROCCHIANO al meeting " + idMeeting, ex);
            return false;
        }

        return true;
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

            List<Partecipation> partecipationsToBeDeleted = new ArrayList<>();
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
