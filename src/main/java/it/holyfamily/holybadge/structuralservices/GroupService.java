package it.holyfamily.holybadge.structuralservices;

import it.holyfamily.holybadge.database.repositories.GroupRepository;
import it.holyfamily.holybadge.database.repositories.MembershipRepository;
import it.holyfamily.holybadge.database.repositories.ParishionerRepository;
import it.holyfamily.holybadge.entities.Group;
import it.holyfamily.holybadge.entities.Membership;
import it.holyfamily.holybadge.entities.Parishioner;
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

    public List<Group> getGroupsList() {

        List<Group> groups = new ArrayList<>();
        try {

            groups = groupsRepository.findAll();

        } catch (Exception ex) {
            logger.error("ERRORE DURANTE IL RECUPERO LA LISTA DI GRUPPI", ex);
        }

        return groups;
    }

    public Group createGroup(Group group) {

        Group createdGroup = null;

        try {
            createdGroup = groupsRepository.save(group);
        } catch (Exception ex) {
            logger.error("ERRORE DURANTE LA CREAZIONE DEL MEETING " + group.getName(), ex);
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

    public List<Parishioner> getGrousMembers (int idGroup){

        List <Parishioner> members = null;
        try {

            members = parishionerRepository.getAllGroupMembers(idGroup);

        }catch (Exception ex){

            logger.info("ERRORE DURANTE IL RECUPERO DEI MEMBRI DEL GRUPPO " + idGroup, ex);

        }

        return members;

    }

    public boolean addSingleParishionerToGroup (int idParishioner, int idGroup) {

        try {

            Membership membership = new Membership();
            membership.setIdParishioner(idParishioner);
            membership.setIdGroup(idGroup);
            return membershipRepository.save(membership) != null;

        } catch (Exception ex) {
            logger.error("ERRORE DURANTE L'AGGIUNTA DEL PARROCCHIANO " + idParishioner + " al meeting " + idGroup, ex);
            return false;
        }

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

    public Group modifyGroup (Group group){

        try {
            return groupsRepository.save(group);
        }catch (Exception ex){
            logger.error("ERRORE DURANTE L'UPDATE DELL'INCONTRO " + group.getId(), ex);
            return null;
        }

    }

}
