package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.ParishionerToAdditional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "it.holyfamily.holybadge.database.repositories.ParishionerToAdditionalRepository")
public interface ParishionerToAdditionalRepository extends CrudRepository<ParishionerToAdditional, Integer> {
}
