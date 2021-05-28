package it.holyfamily.holybadge.controllers;

import it.holyfamily.holybadge.entities.Meeting;
import it.holyfamily.holybadge.entities.Parishioner;
import it.holyfamily.holybadge.structuralservices.MeetingService;
import it.holyfamily.holybadge.structuralservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/holybadge/meetings")
    public ResponseEntity<Object> getMeetingsList (HttpServletRequest request, HttpServletResponse response) {

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                List<Meeting> allMeetings = meetingService.getMeetingsList();

                if (allMeetings != null){
                    return new ResponseEntity<>(allMeetings, HttpStatus.OK);
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

    @GetMapping(value = "/holybadge/meetingPartecipants")
    public ResponseEntity<Object> getMeetingPartecipants (@RequestParam(value = "idMeeting") int idMeeting, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                List<Parishioner> partecipants = meetingService.getMeetingPartecipants(idMeeting);

                if (partecipants != null){
                    return new ResponseEntity<>(partecipants, HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("ERRORE DURANTE RECUPERO PARTECIPANTI", HttpStatus.INTERNAL_SERVER_ERROR);
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

    @RequestMapping(value = "/holybadge/createMeeting",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createMeeting (@RequestBody Meeting meeting, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                Meeting createdMeeting = meetingService.createMeeting(meeting);
                if (createdMeeting != null){
                    return new ResponseEntity<>(createdMeeting, HttpStatus.CREATED);
                }else {
                    return new ResponseEntity<>("ERRORE DURANTE RECUPERO PARTECIPANTI", HttpStatus.INTERNAL_SERVER_ERROR);
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

    @RequestMapping(value = "/holybadge/addGroupToMeeting",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> addGroupToMeeting (@RequestBody String groupName, @RequestBody int idMeeting, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                // Qui ho direttamente l'oggetto meeting al quale devo aggiungere il gruppo, quest'oggetto me lo passa il frontend (ad esempio dopo averlo selezionato
                // con getMeetingsList)
                boolean added = meetingService.addGroupToMeeting(groupName, idMeeting);
                if (added){
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("ERRORE DURANTE RECUPERO PARTECIPANTI", HttpStatus.INTERNAL_SERVER_ERROR);
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

    @RequestMapping(value = "/holybadge/addParishionerToMeeting",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> addParishionerToMeeting (@RequestBody int idParishioner, @RequestBody int idMeeting, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                // Qui ho direttamente l'oggetto meeting al quale devo aggiungere il gruppo, quest'oggetto me lo passa il frontend (ad esempio dopo averlo selezionato
                // con getMeetingsList)
                boolean added = meetingService.addSingleParishionerToMeeting(idParishioner, idMeeting);
                if (added){
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("ERRORE DURANTE RECUPERO PARTECIPANTI", HttpStatus.INTERNAL_SERVER_ERROR);
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

    @RequestMapping(value = "/holybadge/removeParishionerFromMeeting",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeParishionerFromMeeting (@RequestBody int idParishioner, @RequestBody int idMeeting, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                // Qui ho direttamente l'oggetto meeting al quale devo aggiungere il gruppo, quest'oggetto me lo passa il frontend (ad esempio dopo averlo selezionato
                // con getMeetingsList)
                boolean removed = meetingService.removeParishionerFromMeeting(idParishioner, idMeeting);
                if (removed){
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("ERRORE DURANTE ELIMINAZIONE PARTECIPANTE " + idParishioner, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @RequestMapping(value = "/holybadge/removeGroupFromMeeting",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeGroupFromMeeting (@RequestBody String groupName, @RequestBody int idMeeting, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                // Qui ho direttamente l'oggetto meeting al quale devo aggiungere il gruppo, quest'oggetto me lo passa il frontend (ad esempio dopo averlo selezionato
                // con getMeetingsList)
                boolean removed = meetingService.removeGroupFromMeeting(groupName, idMeeting);
                if (removed){
                    return new ResponseEntity<>(true, HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("ERRORE DURANTE ELIMINAZIONE PARTECIPANTI DEL GRUPPO " + groupName, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @RequestMapping(value = "/holybadge/modifyMeeting",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> modifyMeeting (@RequestBody Meeting meeting, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                // Qui ho direttamente l'oggetto meeting al quale devo aggiungere il gruppo, quest'oggetto me lo passa il frontend (ad esempio dopo averlo selezionato
                // con getMeetingsList)
                Meeting modified = meetingService.modifyMeeting(meeting);
                if (modified != null){
                    return new ResponseEntity<>(modified, HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("ERRORE DURANTE MODIFICA DELL'INCONTRO", HttpStatus.INTERNAL_SERVER_ERROR);
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
