package it.holyfamily.holybadge.controllers;


import it.holyfamily.holybadge.entities.Parishioner;
import it.holyfamily.holybadge.structuralservices.ParishionerService;
import it.holyfamily.holybadge.structuralservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowedHeaders = "*")
public class ParishionerController {

    @Autowired
    UserService userservice;

    @Autowired
    ParishionerService parishionerService;

    private static final Logger logger = Logger.getLogger(ParishionerController.class.getName());

    @GetMapping(value = "/holybadge/parishioners")
    public ResponseEntity<Object> getParishionersList (HttpServletRequest request, HttpServletResponse response) {

        try{
            String role = userservice.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                List<Parishioner> allParishioners = parishionerService.getParishionersList();

                if (allParishioners != null){
                    return new ResponseEntity<>(allParishioners, HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("ERRORE DURANTE RECUPERO LISTA INCONTRI", HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }else {
                throw new BadCredentialsException("UTENTE NON AUTORIZZATO");
            }

        }catch(UsernameNotFoundException | BadCredentialsException unfe){
            logger.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity <> (unfe, HttpStatus.UNAUTHORIZED);
        }catch (NullPointerException npe){
            return new ResponseEntity<>(npe, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/holybadge/parishionerDetails")
    public ResponseEntity<Object> getParishionerDetails (HttpServletRequest request, HttpServletResponse response) {

        try{
            String role = userservice.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                List<Parishioner> allParishioners = parishionerService.getParishionersList();

                if (allParishioners != null){
                    return new ResponseEntity<>(allParishioners, HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("ERRORE DURANTE RECUPERO LISTA INCONTRI", HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }else {
                throw new BadCredentialsException("UTENTE NON AUTORIZZATO");
            }

        }catch(UsernameNotFoundException | BadCredentialsException unfe){
            logger.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity <> (unfe, HttpStatus.UNAUTHORIZED);
        }catch (NullPointerException npe){
            return new ResponseEntity<>(npe, HttpStatus.BAD_REQUEST);
        }

    }

}
