package it.holyfamily.holybadge.security.services;

import it.holyfamily.holybadge.entities.User;
import it.holyfamily.holybadge.security.Exception.TokenVerificationException;
import it.holyfamily.holybadge.structuralservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class JWTAuthenticationService implements UserAuthenticationService {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;

    private static final Logger log = Logger.getLogger(JWTAuthenticationService.class.getName());

    // metodo utilizzato nella chiamata di login da controller, crea il token in base allo username e alla password passati.
    // il token che restituisce è quello creato dal metodo create(), altrimenti torna una exception
    @Override
    public String login(String username, String password) throws BadCredentialsException {

        log.info("THIS IS JWTAUTHSERVICE");

        // Questo metodo è per la sola chiamata di login (che è pubblica) e non prevede un'autenticazione via token
        // la sola autenticazione è di username e password, poi genera il token che verrà utilizzato nelle chimate successive
        return userService
                .getByUserName(username)
                .filter(user -> Objects.equals(password, user.getPassword()))
                .map(user -> jwtService.create(user.getId(), user.getRole()))
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password."));
    }

    @Override
    public User authenticateByToken(String token) {
        try {
            // una volta che la signature del token viene verificata prende lo userID
            Object userid = jwtService.verify(token).get("userid");
            return Optional.ofNullable(userid)
                    .flatMap(user -> userService.getByUserID((Integer) userid))
                    .orElseThrow(() -> new UsernameNotFoundException("User '" + userid + "' not found."));
        } catch (TokenVerificationException e) {
            throw new BadCredentialsException("Invalid JWT token.", e);
        }
    }

    // TODO
    @Override
    public void logout(String username) {
    }
}
