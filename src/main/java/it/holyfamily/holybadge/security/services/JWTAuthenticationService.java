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

@Service
public class JWTAuthenticationService implements UserAuthenticationService {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;

    // metodo utilizzato nella chiamata di login da controller, crea il token in base allo username e alla password passati.
    // il token che restituisce è quello creato dal metodo create(), altrimenti torna una exception
    @Override
    public String login(String username, String password) throws BadCredentialsException {
        return userService
                .getByUsername(username)
                .filter(user -> Objects.equals(password, user.getPassword()))
                .map(user -> jwtService.create(username))
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password."));
    }

    @Override
    public User authenticateByToken(String token) {
        try {
            Object username = jwtService.verify(token).get("username");
            return Optional.ofNullable(username)
                    .flatMap(name -> userService.getByUsername(String.valueOf(name)))
                    .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found."));
        } catch (TokenVerificationException e) {
            throw new BadCredentialsException("Invalid JWT token.", e);
        }
    }

    // TODO
    @Override
    public void logout(String username) {
    }
}
