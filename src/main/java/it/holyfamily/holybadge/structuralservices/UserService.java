package it.holyfamily.holybadge.structuralservices;

import it.holyfamily.holybadge.database.repositories.UserRepository;
import it.holyfamily.holybadge.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Qualifier("it.holyfamily.holybadge.database.repositories.UserRepository")
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getByToken(String token) {
        return userRepository.findByToken(token);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}