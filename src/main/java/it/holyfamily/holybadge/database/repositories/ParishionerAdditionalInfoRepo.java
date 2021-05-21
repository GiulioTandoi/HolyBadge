package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.ParishionerAdditionInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "it.holyfamily.holybadge.database.repositories.ParishionerAdditionalInfoRepo")
public interface ParishionerAdditionalInfoRepo extends CrudRepository <ParishionerAdditionInfo, Integer> {

}
