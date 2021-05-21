package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Parishioner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "it.holyfamily.holybadge.database.repositories.ParishionerRepository")
public interface ParishionerRepository extends CrudRepository <Parishioner, Integer> {

}
