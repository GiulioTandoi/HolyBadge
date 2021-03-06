package it.holyfamily.holybadge.controllers;

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

/**
 * PER UTILIZZARE GLI ENDPOINT DI INOUT PARISH DI QUESTO CONTROLLER BASTA CHE LA CHIAMATA SIA CERTIFICATA
 * DAL TOKEN (quindi che l'utente si è loggato con usertname e password validi)
 * NON SERVE CHE IL RUOLO SIA ADMIN
 **/
@RestController
@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
public class RegisterMovementsController {

    private static final Logger log = Logger.getLogger(RegisterMovementsController.class.getName());

    @Autowired
    ParishionerService parishionerService;

    @Autowired
    UserService userService;

    @GetMapping("/holybadge/registerEntrance")
    public ResponseEntity<Object> registerEntranceInParish(@RequestParam(value = "idParishioner") int idParishioner, HttpServletRequest request, HttpServletResponse response) {

        try {
            userService.authenticateCaller(request, response);
        } catch (UsernameNotFoundException | BadCredentialsException unfe) {
            log.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity<>(unfe, HttpStatus.UNAUTHORIZED);
        }

        if (parishionerService.registerEntrance(idParishioner, LocalDateTime.now())) {
            return new ResponseEntity<>("REGISTRAZIONE MOVIMENTO EFFETTUATA", HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>("OPS! QUALCOSA E' ANDATO STORTO, RIPROVA!", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/holybadge/registerExit")
    public ResponseEntity<Object> registerExitFromParish(@RequestParam(value = "idParishioner") int idParishioner, HttpServletRequest request, HttpServletResponse response) {

        try {
            userService.authenticateCaller(request, response);
        } catch (UsernameNotFoundException | BadCredentialsException unfe) {
            log.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity<>(unfe, HttpStatus.UNAUTHORIZED);
        }
        String result = parishionerService.registerExit(idParishioner, LocalDateTime.now());
        if (result.equals("REGISTRAZIONE MOVIMENTO EFFETTUATA")) {
        	log.info(result);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // TODO analisi per fase 2: valutare se inserire un endpoint che consenta agli utenti base di registrarsi in autonomia agli incontri una volta loggati
    /*
    @GetMapping("/holybadge/registerPartecipation")
    public ResponseEntity<Object> registerPartecipationToMeeting (@RequestParam(value = "idMeeting") int idMeeting,
                                                                  @RequestParam(value = "idParishioner") int idParishioner,
                                                                  HttpServletRequest request, HttpServletResponse response){

        User userAuthenticated;
        try{
            userAuthenticated = userService.authenticateCaller(request, response);
        }catch(UsernameNotFoundException | BadCredentialsException unfe){
            log.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity <> (unfe, HttpStatus.UNAUTHORIZED);
        }


        if(parishionerService.registerPartecipationToMeeting(idParishioner, idMeeting, LocalDateTime.now())){
            return new ResponseEntity<>("REGISTRAZIONE MOVIMENTO EFFETTUATA", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("ERRORE DURANTE LA REGISTRAZIONE DEL MOVIMENTO" , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    */


}
