package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Groups;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "it.holyfamily.holybadge.database.repositories.GroupsRepository")
public interface GroupsRepository extends CrudRepository <Groups, Integer> {

}
