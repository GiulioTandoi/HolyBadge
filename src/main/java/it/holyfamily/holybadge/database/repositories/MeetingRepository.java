package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Meeting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "it.holyfamily.holybadge.database.repositories.MeetingRepository")
public interface MeetingRepository extends CrudRepository <Meeting, Integer> {

}
