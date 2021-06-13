package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.Group;
import it.holyfamily.holybadge.entities.Membership;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("it.holyfamily.holybadge.database.repositories.MembershipRepository")
public interface MembershipRepository extends CrudRepository<Membership, Integer> {

    Membership findByIdParishionerAndIdGroup(int idParishioner, int idGroup);

}
