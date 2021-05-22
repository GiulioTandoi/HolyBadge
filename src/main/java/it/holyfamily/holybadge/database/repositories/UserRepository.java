package it.holyfamily.holybadge.database.repositories;

import it.holyfamily.holybadge.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "it.holyfamily.holybadge.database.repositories.UserRepository")
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findById(int userId);

    Optional<User> findByUsername(String username);
}
