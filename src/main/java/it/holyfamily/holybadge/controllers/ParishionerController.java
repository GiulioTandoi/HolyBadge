package it.holyfamily.holybadge.controllers;


import it.holyfamily.holybadge.entities.Meeting;
import it.holyfamily.holybadge.entities.Parishioner;
import it.holyfamily.holybadge.entities.ParishionerAdditionalInfo;
import it.holyfamily.holybadge.pojos.AdditionalInfoToSingleParishionerPojo;
import it.holyfamily.holybadge.pojos.MeetingPojo;
import it.holyfamily.holybadge.pojos.ParishionerAdditionalInfoPojo;
import it.holyfamily.holybadge.pojos.ParishionerPojo;
import it.holyfamily.holybadge.structuralservices.ParishionerService;
import it.holyfamily.holybadge.structuralservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
public class ParishionerController {

    @Autowired
    UserService userService;

    @Autowired
    ParishionerService parishionerService;

    private static final Logger logger = Logger.getLogger(ParishionerController.class.getName());

    @RequestMapping(value = "/holybadge/createParishioner", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createParishioner(@RequestBody ParishionerPojo parishioner, HttpServletRequest request, HttpServletResponse response) {

        try {
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")) {
                Parishioner parishionerCreated = parishionerService.createParishioner(parishioner);
                if (parishionerCreated != null) {
                    return new ResponseEntity<>(parishionerCreated, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>("ERRORE DURANTE LA CREAZIONE DEL PARROCHIANO " + parishioner.getName(), HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                throw new BadCredentialsException("UTENTE NON AUTORIZZATO");
            }

        } catch (UsernameNotFoundException | BadCredentialsException unfe) {
            logger.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity<>(unfe, HttpStatus.UNAUTHORIZED);
        } catch (NullPointerException npe) {
            return new ResponseEntity<>(npe, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/holybadge/modifyParishioner", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> modifyParishioner(@RequestBody Parishioner parishioner, HttpServletRequest request, HttpServletResponse response) {

        try {
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")) {
                // Qui ho direttamente l'oggetto meeting al quale devo aggiungere il gruppo, quest'oggetto me lo passa il frontend (ad esempio dopo averlo selezionato
                // con getMeetingsList)
                Parishioner modified = parishionerService.modifyParishioner(parishioner);
                if (modified != null) {
                    return new ResponseEntity<>(modified, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("ERRORE DURANTE MODIFICA DEL PARISHIONER " + parishioner.getId(), HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                throw new BadCredentialsException("UTENTE NON AUTORIZZATO");
            }

        } catch (UsernameNotFoundException | BadCredentialsException unfe) {
            logger.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity<>(unfe, HttpStatus.UNAUTHORIZED);
        } catch (NullPointerException npe) {
            return new ResponseEntity<>(npe, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/holybadge/parishioners")
    public ResponseEntity<Object> getParishionersList(HttpServletRequest request, HttpServletResponse response) {

        try {
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")) {
                List<Parishioner> allParishioners = parishionerService.getParishionersList();

                if (allParishioners != null) {
                    return new ResponseEntity<>(allParishioners, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("ERRORE DURANTE RECUPERO LISTA INCONTRI", HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                throw new BadCredentialsException("UTENTE NON AUTORIZZATO");
            }

        } catch (UsernameNotFoundException | BadCredentialsException unfe) {
            logger.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity<>(unfe, HttpStatus.UNAUTHORIZED);
        } catch (NullPointerException npe) {
            return new ResponseEntity<>(npe, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/holybadge/parishionerDetails")
    public ResponseEntity<Object> getParishionerDetails(@RequestParam(value = "idParishioner") int idParishioner, HttpServletRequest request, HttpServletResponse response) {

        try {
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")) {
                Parishioner parishionerBaseInfo = parishionerService.getParishionerBaseInfo(idParishioner);
                List<ParishionerAdditionalInfo> parishionerAdditionalInfo = parishionerService.getParishionerAdditionalInfo(idParishioner);
                HashMap<String, Object> allParishionersDetails = new HashMap<>();
                allParishionersDetails.put("parishionerBaseInfo", parishionerBaseInfo);
                allParishionersDetails.put("additionalInfos", parishionerAdditionalInfo);
                if (allParishionersDetails != null) {
                    return new ResponseEntity<>(allParishionersDetails, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("ERRORE DURANTE RECUPERO DETTAGLI DEL PARROCCHIANO " + idParishioner, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                throw new BadCredentialsException("UTENTE NON AUTORIZZATO");
            }

        } catch (UsernameNotFoundException | BadCredentialsException unfe) {
            logger.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity<>(unfe, HttpStatus.UNAUTHORIZED);
        } catch (NullPointerException npe) {
            return new ResponseEntity<>(npe, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/holybadge/addAdditionalInfoToAll", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> addAdditionalInfoToAll(@RequestBody ParishionerAdditionalInfoPojo additionalInfoPojo, HttpServletRequest request, HttpServletResponse response) {

        try {
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")) {
                ParishionerAdditionalInfo additionalInfoCreated = parishionerService.addAdditionalInfoToAll(additionalInfoPojo);
                if (additionalInfoCreated != null) {
                    return new ResponseEntity<>(additionalInfoCreated, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>("ERRORE DURANTE RECUPERO PARTECIPANTI", HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                throw new BadCredentialsException("UTENTE NON AUTORIZZATO");
            }

        } catch (UsernameNotFoundException | BadCredentialsException unfe) {
            logger.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity<>(unfe, HttpStatus.UNAUTHORIZED);
        } catch (NullPointerException npe) {
            return new ResponseEntity<>(npe, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/holybadge/addAdditionalInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> addAdditionalInfoToSingleParishioner(@RequestBody AdditionalInfoToSingleParishionerPojo params, HttpServletRequest request, HttpServletResponse response) {

        try {
            String role = userService.authenticateCaller(request, response).getRole();
            ParishionerAdditionalInfoPojo parishionerAdditionalInfoPojo = new ParishionerAdditionalInfoPojo();
            parishionerAdditionalInfoPojo.setInfoValue(params.getInfoValue());
            parishionerAdditionalInfoPojo.setInfoName(params.getInfoName());
            if (role.equals("admin")) {
                HashMap<String, Object> additionalInfoCreated = parishionerService.addAdditionalInfoToSingleParishioner(
                        parishionerAdditionalInfoPojo, params.getIdParishioner());
                if (additionalInfoCreated != null) {
                    return new ResponseEntity<>(additionalInfoCreated, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>("ERRORE DURANTE L'AGGIUNTA DELLA INFO AGGIUNTIVA SUL PARROCCHIANO " + params.getIdParishioner(), HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                throw new BadCredentialsException("UTENTE NON AUTORIZZATO");
            }

        } catch (UsernameNotFoundException | BadCredentialsException unfe) {
            logger.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity<>(unfe, HttpStatus.UNAUTHORIZED);
        } catch (NullPointerException npe) {
            return new ResponseEntity<>(npe, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/holybadge/removeParishionerAdditionalInfo", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeParishionerAdditionalInfo(@RequestParam("idParishioner") int idParishioner, @RequestParam("idAdditioanalInfo") int idAdditioanalInfo, HttpServletRequest request, HttpServletResponse response) {

        try {
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")) {
                // Qui ho direttamente l'oggetto meeting al quale devo aggiungere il gruppo, quest'oggetto me lo passa il frontend (ad esempio dopo averlo selezionato
                // con getMeetingsList)
                boolean removed = parishionerService.removeParishionerAdditionalInfo(idParishioner, idAdditioanalInfo);
                if (removed) {
                    return new ResponseEntity<>(true, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("ERRORE DURANTE ELIMINAZIONE DELLE INFO AGGIUNTIVE DEL PARROCCHIANO " + idParishioner, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                throw new BadCredentialsException("UTENTE NON AUTORIZZATO");
            }

        } catch (UsernameNotFoundException | BadCredentialsException unfe) {
            logger.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity<>(unfe, HttpStatus.UNAUTHORIZED);
        } catch (NullPointerException npe) {
            return new ResponseEntity<>(npe, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/holybadge/removeParishioner", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeParishioner(@RequestParam("idParishioner") int idParishioner, HttpServletRequest request, HttpServletResponse response) {

        try {
            String role = userService.authenticateCaller(request, response).getRole();

            if (role.equals("admin")) {
                // Qui ho direttamente l'oggetto meeting al quale devo aggiungere il gruppo, quest'oggetto me lo passa il frontend (ad esempio dopo averlo selezionato
                // con getMeetingsList)
                boolean removed = parishionerService.removeParishioner(idParishioner);
                if (removed) {
                    return new ResponseEntity<>(true, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("ERRORE DURANTE L'ELIMINAZIONE DEL PARROCCHIANO " + idParishioner, HttpStatus.INTERNAL_SERVER_ERROR);
                }

            } else {
                throw new BadCredentialsException("UTENTE NON AUTORIZZATO");
            }

        } catch (UsernameNotFoundException | BadCredentialsException unfe) {
            logger.info("CHIAMATA NON AUTORIZZATA");
            return new ResponseEntity<>(unfe, HttpStatus.UNAUTHORIZED);
        } catch (NullPointerException npe) {
            return new ResponseEntity<>(npe, HttpStatus.BAD_REQUEST);
        }

    }

}
