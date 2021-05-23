package it.holyfamily.holybadge.security.configuration;

import it.holyfamily.holybadge.database.repositories.UserRepository;
import it.holyfamily.holybadge.entities.User;
import it.holyfamily.holybadge.security.configuration.TokenAuthenticationFilter;
import it.holyfamily.holybadge.security.configuration.TokenAuthenticationProvider;
import it.holyfamily.holybadge.security.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Optional;

@Service
public class UserService {

    @Qualifier("it.holyfamily.holybadge.database.repositories.UserRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("JWTAuthenticationService")
    @Autowired
    UserAuthenticationService userAuthService;

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

    public User authenticateCaller (HttpServletRequest request, HttpServletResponse response){

        return userAuthService.authenticateByToken(request.getHeader(AUTHORIZATION).replace(BEARER, "").trim());
    }
}