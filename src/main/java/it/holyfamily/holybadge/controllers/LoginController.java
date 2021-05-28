package it.holyfamily.holybadge.controllers;

import it.holyfamily.holybadge.pojos.UserCredentialsPojo;
import it.holyfamily.holybadge.security.services.UserAuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowedHeaders = "*")
public class LoginController {

    @Qualifier("JWTAuthenticationService")
    @Autowired
    UserAuthenticationService userAuthService;

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @RequestMapping(value = "/holybadge/authenticate",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getUser(@RequestBody UserCredentialsPojo formBody) {

        try{
            logger.info("LOGIN CALLED");
            return new ResponseEntity<>(userAuthService.login(formBody.getUsername(), formBody.getPassword()), HttpStatus.OK);
        }catch (BadCredentialsException bce){
            return new ResponseEntity<>(bce, HttpStatus.UNAUTHORIZED);
        }

    }

}
