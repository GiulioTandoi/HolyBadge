package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "it.holyfamily.holybadge.database.repositories.MeetingRepository")
public interface MeetingRepository extends CrudRepository<Meeting, Integer> {

    List<Meeting> findAllByOrderByDateDesc();

    @Query(value = "SELECT m.id, m.date, m.location, m.meetingName FROM meeting m LEFT JOIN partecipation p ON p.idMeeting = m.id WHERE p.idParishioner = :idParishioner", nativeQuery = true)
    List <Meeting> getAllParishionerPartecipations(@Param("idParishioner") int idParishioner);

    @Query(value = "SELECT m.id, m.date, m.location, m.meetingName FROM meeting m WHERE m.id NOT IN (SELECT p.idMeeting from partecipation p WHERE p.idParishioner = :idParishioner)", nativeQuery = true)
    List<Meeting> getAllPossibleMeetingsForParishioner(@Param("idParishioner") int idParishioner);
}
