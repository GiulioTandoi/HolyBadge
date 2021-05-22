package it.holyfamily.holybadge.controllers;

import it.holyfamily.holybadge.pojos.LoginPojo;
import it.holyfamily.holybadge.security.services.UserAuthenticationService;
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

    @RequestMapping(value = "/holybadge/authenticate",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getUser(@RequestBody LoginPojo formBody) {

        System.out.println("I'M LOGIN CONTROLLER");
        try{
            return new ResponseEntity<>(userAuthService.login(formBody.getUsername(), formBody.getPassword()), HttpStatus.OK);
        }catch (BadCredentialsException bce){
            return new ResponseEntity<>(bce, HttpStatus.UNAUTHORIZED);
        }

    }

}
