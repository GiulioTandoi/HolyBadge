package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Membership;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("it.holyfamily.holybadge.database.repositories.MembershipRepository")
public interface MembershipRepository extends CrudRepository<Membership, Integer> {

    Membership findByIdParishionerAndIdGroup(int idParishioner, int idGroup);

    @Query(value = "SELECT g.name FROM parishgroup g LEFT JOIN membership m ON m.idGroup = g.id WHERE m.idParishioner = :idParishioner", nativeQuery = true)
    List<String> getGroupNamesOfParishioner(int idParishioner);
}
