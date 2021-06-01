package it.holyfamily.holybadge.structuralservices;

import it.holyfamily.holybadge.database.repositories.UserRepository;
import it.holyfamily.holybadge.entities.User;
import it.holyfamily.holybadge.pojos.RegisterUserPojo;
import it.holyfamily.holybadge.security.services.UserAuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class UserService {

    @Qualifier("it.holyfamily.holybadge.database.repositories.UserRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("JWTAuthenticationService")
    @Autowired
    UserAuthenticationService userAuthService;

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getByUserID(int userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public User authenticateCaller(HttpServletRequest request, HttpServletResponse response) {

        return userAuthService.authenticateByToken(request.getHeader(AUTHORIZATION).replace(BEARER, "").trim());
    }

    public User registerUser(RegisterUserPojo toBeRegistered) {

        try {
            User user = new User();
            user.setUsername(toBeRegistered.getUsername());
            user.setPassword(toBeRegistered.getPassword());
            user.setRole(toBeRegistered.getRole());
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("ERRORE DURANTE LA CREAZIONE DELLO USER ", e);
            return null;
        }

    }
}