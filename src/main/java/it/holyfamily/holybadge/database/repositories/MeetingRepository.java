package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository(value = "it.holyfamily.holybadge.database.repositories.MeetingRepository")
public interface MeetingRepository extends CrudRepository <Meeting, Integer> {

//    List<Meeting> findAllByOrderByDateDesc(Pageable pageable);

    @Query(value = "SELECT * FROM meeting WHERE date >= :date", nativeQuery = true)
    List <Meeting> findAllByOrderByDateDescGreaterThen(LocalDateTime date);

}
