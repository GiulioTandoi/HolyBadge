package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Movements;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "it.holyfamily.holybadge.database.repositories.MovementsRepository")
public interface MovementsRepository extends CrudRepository <Movements, Integer> {

}
