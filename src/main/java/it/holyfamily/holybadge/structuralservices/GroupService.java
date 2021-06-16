package it.holyfamily.holybadge.structuralservices;

import it.holyfamily.holybadge.database.repositories.GroupRepository;

import it.holyfamily.holybadge.database.repositories.MembershipRepository;
import it.holyfamily.holybadge.database.repositories.ParishionerRepository;
import it.holyfamily.holybadge.entities.Group;
import it.holyfamily.holybadge.entities.Membership;
import it.holyfamily.holybadge.entities.Parishioner;
import it.holyfamily.holybadge.pojos.GroupPojo;
import it.holyfamily.holybadge.pojos.ParishionersOfGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Qualifier("it.holyfamily.holybadge.database.repositories.ParishionerRepository")
    @Autowired
    ParishionerRepository parishionerRepository;

    @Qualifier("it.holyfamily.holybadge.database.repositories.MembershipRepository")
    @Autowired
    MembershipRepository membershipRepository;

    @Qualifier("it.holyfamily.holybadge.database.repositories.GroupRepository")
    @Autowired
    GroupRepository groupsRepository;

    private static final Logger logger = LoggerFactory.getLogger(MeetingService.class);

    public Group getGroupDetails(int idGroup){
        try{
            return groupsRepository.findById(idGroup).get();
        }catch (Exception ex ){
            logger.error("ERRORE DURANTE IL RECUPERO DEI DETTAGLI DEL GRUPPO", ex);
        }
        return null;
    }


    public List<Group> getGroupsList() {

        List<Group> groups = new ArrayList<>();
        try {

            groups = groupsRepository.findAll();

        } catch (Exception ex) {
            logger.error("ERRORE DURANTE IL RECUPERO LA LISTA DI GRUPPI", ex);
        }

        return groups;
    }

    public Group createGroup(GroupPojo group) {

        Group createdGroup = new Group(group);

        try {
            createdGroup = groupsRepository.save(createdGroup);
        } catch (Exception ex) {
            logger.error("ERRORE DURANTE LA CREAZIONE DEL MEETING " + createdGroup.getName(), ex);
        }

        return createdGroup;
    }

    public boolean deleteGroup(int groupId) {

        try {
            Optional<Group> group = groupsRepository.findById(groupId);

            if (group.isPresent()) {

                groupsRepository.delete(group.get());
                return true;
            } else {
                logger.info("IL GRUPPO DA CANCELLARE CON ID " + groupId + " NON E' PRESENTE SUL DB");
                return false;
            }

        } catch (Exception ex) {
            logger.info("ERRORE DURANTE LA RIMOZIONE DEL GRUPPO " + groupId, ex);
            return false;
        }

    }

    public List<ParishionersOfGroup> getGroupMembers(int idGroup) {

        List<Parishioner> members;
        List<ParishionersOfGroup> parishioners = new ArrayList<>();
        try {

            members = parishionerRepository.getAllGroupMembers(idGroup);
            ParishionersOfGroup ofGroup;
            for (Parishioner parishioner: members){
                ofGroup = new ParishionersOfGroup(parishioner, true);
                parishioners.add(ofGroup);
            }
            
        } catch (Exception ex) {

            logger.info("ERRORE DURANTE IL RECUPERO DEI MEMBRI DEL GRUPPO " + idGroup, ex);
            return null;
        }

        return parishioners;

    }
    
    public List<ParishionersOfGroup> getGroupNotMembers(int idGroup) {

        List<Parishioner> members;
        List<ParishionersOfGroup> parishioners = new ArrayList<>();
        try {

            members = parishionerRepository.getAllGroupMembers(idGroup);
            List <Parishioner> allNotMembers = (List<Parishioner>) parishionerRepository.findAll();
            allNotMembers.removeAll(members);
            ParishionersOfGroup notOfGroup;
            for (Parishioner parishioner: allNotMembers){
            	notOfGroup = new ParishionersOfGroup(parishioner, true);
                parishioners.add(notOfGroup);
            }
            
        } catch (Exception ex) {

            logger.info("ERRORE DURANTE IL RECUPERO DEI MEMBRI DEL GRUPPO " + idGroup, ex);
            return null;
        }

        return parishioners;

    }

    public boolean addParishionerListToGroup(List<Integer> idParishioners, int idGroup) {

        Membership membership;
        try {
            for (int idParishioner : idParishioners){
                membership = new Membership();
                membership.setIdParishioner(idParishioner);
                membership.setIdGroup(idGroup);
                membershipRepository.save(membership);
            }

        }catch (Exception ex) {
            logger.error("ERRORE DURANTE L'AGGIUNTA DELLA LISTA DEI PARROCCHIANI AL GFRUPPO " + idGroup, ex);
            return false;
        }

        return true;

    }

    public boolean addParishionerToGroup(Integer idParishioner, int idGroup) {

        Membership membership;
        try {
            membership = new Membership();
            membership.setIdParishioner(idParishioner);
            membership.setIdGroup(idGroup);
            membershipRepository.save(membership);
        }catch (Exception ex) {
            logger.error("ERRORE DURANTE L'AGGIUNTA DEL PARROCCHIANO AL GFRUPPO " + idGroup, ex);
            return false;
        }

        return true;

    }

    public boolean removeParishionerFromGroup(int idParishioner, int idGroup) {

        try {

            membershipRepository.delete(membershipRepository.findByIdParishionerAndIdGroup(idParishioner, idGroup));
            return true;

        } catch (Exception ex) {
            logger.error("ERRORE DURANTE LA RIMOZIONE DEL PARROCCHIANO " + idParishioner + " dal meeting " + idGroup, ex);
            return false;
        }

    }

    public Group modifyGroup(Group group) {

        try {
            return groupsRepository.save(group);
        } catch (Exception ex) {
            logger.error("ERRORE DURANTE L'UPDATE DEL GRUPPO " + group.getId(), ex);
            return null;
        }

    }

}
