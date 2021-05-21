package it.holyfamily.holybadge.security.configuration;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

//Tutte le richieste da autorizzare dovranno essere processate da un apposito authentication filter
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    protected TokenAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    //Il metodo attemptAuthentication, ereditato dalla classe di supporto AbstractAuthenticationProcessingFilter,
    // implementa una logica piuttosto semplice: andiamo ad estrarre dall’header della richiesta la voce Authorization e
    // creiamo un oggetto UsernamePasswordAuthenticationToken valorizzandolo con il token ricevuto.
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) {
            // La voce Bearer è sempre presente nel campo Authentication delle chiamate tokenizzate e precede sempre il valore del Token
            String token = Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .map(v -> v.replace(BEARER, "").trim())
                .orElseThrow(() -> new BadCredentialsException("Missing authentication token."));

        Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
        return getAuthenticationManager().authenticate(auth);
    }

}
