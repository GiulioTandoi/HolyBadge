package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Parishioner;
import it.holyfamily.holybadge.entities.ParishionerAdditionalInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "it.holyfamily.holybadge.database.repositories.ParishionerRepository")
public interface ParishionerRepository extends CrudRepository<Parishioner, Integer> {

    @Query(value = "SELECT p.id,p.name,p.surname,p.note,p.phoneNumber FROM parishioner p LEFT JOIN partecipation part ON p.id = part.idParishioner LEFT JOIN meeting m ON m.id = part.idMeeting WHERE part.idMeeting = :idMeeting", nativeQuery = true)
    List<Parishioner> getAllMeetingPartecipants(int idMeeting);

    @Query(value = "SELECT p.id,p.name,p.surname,p.note,p.phoneNumber FROM parishioner p LEFT JOIN membership memb ON p.id = memb.idParishioner LEFT JOIN parishgroup g ON g.id = memb.idGroup WHERE g.name = :groupName", nativeQuery = true)
    List<Parishioner> getAllGroupMembers(String groupName);

    @Query(value = "SELECT p.id,p.name,p.surname,p.note,p.phoneNumber FROM parishioner p LEFT JOIN membership memb ON p.id = memb.idParishioner LEFT JOIN parishgroup g ON g.id = memb.idGroup WHERE g.id = :idGroup", nativeQuery = true)
    List<Parishioner> getAllGroupMembers(int idGroup);

    @Query(value = "SELECT p.id,p.name,p.surname,p.note,p.phoneNumber FROM parishioner p LEFT JOIN membership memb ON p.id = memb.idParishioner LEFT JOIN parishgroup g ON g.id = memb.idGroup WHERE g.id <> :idGroup OR p.id NOT IN (SELECT m.idParishioner FROM membership m)", nativeQuery = true)
    List<Parishioner> getNotGroupMemebers(int idGroup);

}
