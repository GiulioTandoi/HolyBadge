package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Partecipation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository(value = "it.holyfamily.holybadge.database.repositories.PartecipationRepository")
public interface PartecipationRepository extends CrudRepository<Partecipation, Integer> {

    List<Partecipation> findAllByIdParishioner(int idMeeting);

    @Query(value = "SELECT part.idParishioner FROM partecipation part", nativeQuery = true)
    List<Integer> getIdParishionersByIdMeeting(int idMeeting);

    Partecipation findByIdParishionerAndIdMeeting(int idParishioner, int idMeeting);

    List<Partecipation> findByIdParishionerAndIdMeetingAndPartecipatedNull(int idParishioner, int idMeeting, Pageable firstOne);

    @Query(value = "SELECT part.idMeeting FROM partecipation part LEFT JOIN meeting m ON part.idMeeting = m.id WHERE part.idParishioner = :idParishioner AND m.date < :exitTime AND m.date > :entranceTime", nativeQuery = true)
    List<Integer> getAllMeetingsIdBeforeExit(int idParishioner, LocalDateTime exitTime, LocalDateTime entranceTime);

}
