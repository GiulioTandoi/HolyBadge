package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "it.holyfamily.holybadge.database.repositories.GroupRepository")
public interface GroupRepository extends CrudRepository<Group, Integer> {

    List<Group> findAll();

    @Query(value = "SELECT g.id, g.name FROM parishgroup g LEFT JOIN membership m ON m.idGroup = g.id WHERE m.idParishioner = :idParishioner", nativeQuery = true)
    List<Group> getGroupNamesOfParishioner(int idParishioner);

}
