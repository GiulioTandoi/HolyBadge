package it.holyfamily.holybadge.security.configuration;

import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Solitamente, l’autenticazione tramite form prevede,
//in caso di credenziali errate o di errori lato server, il redirect alla pagina di login (o quantomeno ad una pagina di errore).
//Nel caso di REST API, invece, il server dovrà semplicemente restituire un errore 401, Unauthorized. Di conseguenza il metodo sendRedirect non dovrà fare nulla
public class NoRedirectStrategy implements RedirectStrategy {
    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) {
    }
}