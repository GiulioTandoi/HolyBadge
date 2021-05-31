package it.holyfamily.holybadge.controllers;

import it.holyfamily.holybadge.entities.User;
import it.holyfamily.holybadge.pojos.RegisterUserPojo;
import it.holyfamily.holybadge.pojos.UserCredentialsPojo;
import it.holyfamily.holybadge.security.services.UserAuthenticationService;
import it.holyfamily.holybadge.structuralservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
public class UserController {

    @Qualifier("JWTAuthenticationService")
    @Autowired
    UserAuthenticationService userAuthService;

    @Autowired
    UserService userService;

    public enum ROLE {
        admin,
        baseuser
    }

    @RequestMapping(value = "/holybadge/authenticate",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getUser(@RequestBody UserCredentialsPojo formBody) {

        try{
            HashMap<String, String> loggedObject = new HashMap<>();
            loggedObject.put("token", userAuthService.login(formBody.getUsername(), formBody.getPassword()));
            loggedObject.put("role", userService.getByUserName(formBody.getUsername()).get().getRole());
            return new ResponseEntity<>(loggedObject, HttpStatus.OK);
        }catch (BadCredentialsException bce){
            return new ResponseEntity<>(bce, HttpStatus.UNAUTHORIZED);
        }

    }

    @RequestMapping(value = "/holybadge/registerUser",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> registerUser(@RequestBody RegisterUserPojo formBody, HttpServletRequest request, HttpServletResponse response) {

        try{
            userService.authenticateCaller(request, response);
            if (ROLE.valueOf(formBody.getRole()).equals(ROLE.admin) ||
                    ROLE.valueOf(formBody.getRole()).equals(ROLE.baseuser)){
                User user = userService.registerUser(formBody);
                if (user != null){
                    return new ResponseEntity<>(user, HttpStatus.CREATED);
                }else{
                    return new ResponseEntity<>("ERRORE DURANTE LA CREAZIONE DELLO USER", HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }else{
                return new ResponseEntity<>("RUOLO NON PREVISTO", HttpStatus.BAD_REQUEST);
            }

        }catch (BadCredentialsException | UsernameNotFoundException un){
            return new ResponseEntity<>(un, HttpStatus.UNAUTHORIZED);
        }


    }

}
