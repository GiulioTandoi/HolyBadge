package it.holyfamily.holybadge.controllers;

import it.holyfamily.holybadge.structuralservices.ParishionerService;
import it.holyfamily.holybadge.structuralservices.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
public class HomePageController {

    @Autowired
    UserService userservice;

    @Autowired
    ParishionerService parishionerService;

    private static final Logger logger = Logger.getLogger(HomePageController.class.getName());

    @GetMapping(value = "/holybadge/homepage")
    public ResponseEntity<Object> getInoutOutMovementsList(HttpServletRequest request, HttpServletResponse response) {

        try {
            String role = userservice.authenticateCaller(request, response).getRole();

            if (role.equals("admin")) {
                List<HashMap<String, Object>> inOutMovementsList = parishionerService.getInOutMovements();

                if (inOutMovementsList != null) {
                    return new ResponseEntity<>(inOutMovementsList, HttpStatus.OK);
                } else {
                    String errorMessage = "ERRORE DURANTE IL RECUPERO DEI MOVIMENTI DEI PARROCCHIANI, ALCUNI PARROCCHIANI NON SONO REGISTRATI";
                    logger.error(errorMessage);
                    return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                return new ResponseEntity<>("UTENTE BASIC NON AUTORIZZATO PER QUESTO TIPO DI CHIAMATA", HttpStatus.FORBIDDEN);
            }
        } catch (BadCredentialsException bce) {
            return new ResponseEntity<>(bce, HttpStatus.UNAUTHORIZED);
        }

    }

}
