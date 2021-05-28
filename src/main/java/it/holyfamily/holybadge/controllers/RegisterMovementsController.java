package it.holyfamily.holybadge.controllers;

import it.holyfamily.holybadge.entities.User;
import it.holyfamily.holybadge.structuralservices.UserService;
import it.holyfamily.holybadge.structuralservices.ParishionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowedHeaders = "*")
public class RegisterMovementsController {

    private static final Logger log = Logger.getLogger(RegisterMovementsController.class.getName());

    @Autowired
    ParishionerService parishionerService;

    @Autowired
    UserService userService;

    @GetMapping("/holybadge/registerEntrance")
    public ResponseEntity<Object> registerEntranceInParish(HttpServletRequest request, HttpServletResponse response){
        User userAuthenticated;
        try{
            userAuthenticated = userService.authenticateCaller(request, response);
        }catch(UsernameNotFoundException | BadCredentialsException unfe){
            log.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity <> (unfe, HttpStatus.UNAUTHORIZED);
        }

        if(parishionerService.registerEntrance(userAuthenticated.getId(), LocalDateTime.now())){
            return new ResponseEntity<>("REGISTRAZIONE MOVIMENTO EFFETTUATA", HttpStatus.CREATED);
        }else {

            return new ResponseEntity<>("ERRORE DURANTE LA REGISTRAZIONE DEL MOVIMENTO" , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/holybadge/registerExit")
    public ResponseEntity<Object> registerExitFromParish (HttpServletRequest request, HttpServletResponse response){
        User userAuthenticated;
        try{
            userAuthenticated = userService.authenticateCaller(request, response);
        }catch(UsernameNotFoundException | BadCredentialsException unfe){
            log.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity <> (unfe, HttpStatus.UNAUTHORIZED);
        }

        if(parishionerService.registerExit(userAuthenticated.getId(), LocalDateTime.now())){
            return new ResponseEntity<>("REGISTRAZIONE MOVIMENTO EFFETTUATA", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("ERRORE DURANTE LA REGISTRAZIONE DEL MOVIMENTO" , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
