package it.holyfamily.holybadge.controllers;

import it.holyfamily.holybadge.entities.Group;
import it.holyfamily.holybadge.entities.Meeting;
import it.holyfamily.holybadge.entities.Parishioner;
import it.holyfamily.holybadge.structuralservices.GroupService;
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
public class GroupsController {

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupsService;

    private static final Logger logger = Logger.getLogger(GroupsController.class.getName());

    @GetMapping(value = "/holybadge/groups")
    public ResponseEntity<Object> getGroupsList (HttpServletRequest request, HttpServletResponse response) {

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                List<Group> allGroups = groupsService.getGroupsList();

                if (allGroups != null){
                    return new ResponseEntity<>(allGroups, HttpStatus.OK);
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

    @GetMapping(value = "/holybadge/groupsMembers")
    public ResponseEntity<Object> getGroupMembers (@RequestParam(value = "idGroup") int idGroup, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                List<Parishioner> members = groupsService.getGrousMembers(idGroup);

                if (members != null){
                    return new ResponseEntity<>(members, HttpStatus.OK);
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

    @RequestMapping(value = "/holybadge/createGroup",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createGroup (@RequestBody Group group, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                Group createdGroup = groupsService.createGroup(group);
                if (createdGroup != null){
                    return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
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

    @RequestMapping(value = "/holybadge/addParishionerToGroup",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> addParishionerToGroup (@RequestBody int idParishioner, @RequestBody int idGroup, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                // Qui ho direttamente l'oggetto meeting al quale devo aggiungere il gruppo, quest'oggetto me lo passa il frontend (ad esempio dopo averlo selezionato
                // con getMeetingsList)
                boolean added = groupsService.addSingleParishionerToGroup(idParishioner, idGroup);
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

    @RequestMapping(value = "/holybadge/removeParishionerFromGroup",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeParishionerFromGroup (@RequestBody int idParishioner, @RequestBody int idGroup, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                // Qui ho direttamente l'oggetto meeting al quale devo aggiungere il gruppo, quest'oggetto me lo passa il frontend (ad esempio dopo averlo selezionato
                // con getMeetingsList)
                boolean removed = groupsService.removeParishionerFromGroup(idParishioner, idGroup);
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

    @RequestMapping(value = "/holybadge/modifyGroup",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> modifyGroup (@RequestBody Group group, HttpServletRequest request, HttpServletResponse response){

        try{
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")){
                // Qui ho direttamente l'oggetto meeting al quale devo aggiungere il gruppo, quest'oggetto me lo passa il frontend (ad esempio dopo averlo selezionato
                // con getMeetingsList)
                Group modified = groupsService.modifyGroup(group);
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
