package it.holyfamily.holybadge.controllers;

import it.holyfamily.holybadge.entities.InOutParish;
import it.holyfamily.holybadge.entities.Meeting;
import it.holyfamily.holybadge.structuralservices.MeetingService;
import it.holyfamily.holybadge.structuralservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowedHeaders = "*")
public class MeetingController {

    @Autowired
    UserService userService;

    @Autowired
    MeetingService meetingService;

    private static final Logger logger = Logger.getLogger(MeetingController.class.getName());

    @GetMapping(value = "/holybadge/homepage")
    public ResponseEntity<Object> getMeetingsList (HttpServletRequest request, HttpServletResponse response) {

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                // TODO creare il metodo sul service
                List<Meeting> allMeetings = meetingService.getMeetingsList();

                if (allMeetings != null){
                    return new ResponseEntity<>(allMeetings, HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("ERRORE DURANTE ", HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }else {
                return new ResponseEntity<>("UTENTE BASIC NON AUTORIZZATO PER QUESTO TIPO DI CHIAMATA", HttpStatus.FORBIDDEN);
            }
        }catch (BadCredentialsException bce){
            return new ResponseEntity<>(bce, HttpStatus.UNAUTHORIZED);
        }

    }
}
