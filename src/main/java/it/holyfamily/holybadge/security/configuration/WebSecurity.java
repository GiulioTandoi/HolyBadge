package it.holyfamily.holybadge.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// classe di configurazione degli accessi all'applicazione
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    // --- Di default spring e java attivano schemratura contro attacchi csrf (Cross site request forgery, ovvero, falsificazione di una richiesta da un sito esterno)
    // nel configurerAdapter posso disabilitare questa sicurezza se non effettuo le chiamate direttamente dal browser, come in quato caso (le chiamate passano sempre per il frontend che si autentica tramite token,
    // quindi non c'è bisogno di questa protezine
    // --- Gli antmatchers invece servono per configurare differenti accessi a seconda delle api che vengono chiamate, per esempio posso impostare un
    // certo livello di autenticazione per determinate api scrivendo .antMatcher("/auth/*"), tutte le api che cominciano così dovranno rispettare i vincoli che imposto
    // in quel specifico metodo dell'adapter

    // 	.authorizeRequests().anyRequest().authenticated() significa che ogni richiesta necessita che lo user sia autenticato
    //	.formLogin().loginPage("/login"); sinifica che l'autenticazione è pensata tramite login e carico in questo modo una login custom all'endpoint /login
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Quando il servizio di autorizzazione reindirizza a un URL non consentito, l'applicazione reindirizzerà al servizio di autorizzazione
        // per un'ulteriore autenticazione.
        // Il processo entra in un ciclo che non finisce, provocando il verificarsi di ERR_TOO_MANY_REDIRECTS.

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/holybadge/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        //permitAll() This will allow the public access that is anyone can access endpoint "/", "/holybadge/**" without authentication.
        //anyRequest().authenticated() will restrict the access for any other endpoint other than "/", "/holybadge/**", and the user must be authenticated.
    }

}
