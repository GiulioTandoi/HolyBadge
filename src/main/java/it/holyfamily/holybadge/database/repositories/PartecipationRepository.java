package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Partecipation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "it.holyfamily.holybadge.database.repositories.PartecipationRepository")
public interface PartecipationRepository extends CrudRepository<Partecipation, Integer> {

    List<Partecipation> findAllByIdMeeting(int idMeeting);

    Partecipation findByIdParishionerAndIdMeeting(int idParishioner, int idMeeting);

}
