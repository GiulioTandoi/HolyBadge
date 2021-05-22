package it.holyfamily.holybadge.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.holyfamily.holybadge.security.Exception.TokenVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class JWTService {
    private Algorithm algorithm;
    private int defaultExpiration;

    // il jwt.secret è la chiave che serve per generare la signature criptata che viene inserita nel token in modo che ogni volta che il middleware riceve una
    // chiamata API con quel token legge la signature e valida il token facendo proiseguire la chimata
    public JWTService(
            @Value("jwt.secret") String secret,
            @Value("${jwt.defaultExpirationMillis}") int defaultExpirationMillis) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.defaultExpiration = defaultExpirationMillis;

    }

    // Crea un token con data di creazione, durata e richiede lo userid e il ruolo (li aggiunge come claim nel contenuto del token),
    // utilizza l'algoritmo HMAC256 per generare la signature del token
    public String create(int userId, String role) {
        Instant issuedAt = Instant.now();

        // la composizione del token quindi sarà: un header criptato in base64, un payload contenente i dati (userid, role, issuead at e expiration) in base64 e una
        // signature generata a partire da un jwt.secret che servirà per validare le chiamate, quando legge la signature del token la decodifica (vedi metodo
        // verify di questa classe)
        return JWT.create()
                .withIssuedAt(Date.from(issuedAt))
                .withExpiresAt(Date.from(issuedAt.plusSeconds(defaultExpiration)))
                .withClaim("userid", userId)
                .withClaim("role", role)
                .sign(algorithm);
    }

    public Map<String, Object> verify(String token) throws TokenVerificationException {
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims().entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().as(Object.class)));
        } catch (Exception e) {
            throw new TokenVerificationException(e);
        }
    }
}