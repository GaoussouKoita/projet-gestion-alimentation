package ml.pic.tech.app.alimentation.securite.config;

import ml.pic.tech.app.alimentation.securite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;


/**
 * Classe de configuration de spring security a base de beans
 * Elle definit deux beans:
 * 1-Pour la securisation des ressources
 * 2-Definit la maniere de charges les users et leurs roles
 */
@EnableWebSecurity
public class SecuriteConfig {

    @Autowired
    private AccountService service;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(service);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().antMatchers("/login", "/error", "/css/**", "/js/**", "/webfonts/**").permitAll().
                and().authorizeRequests().antMatchers("/utilisateur/**").hasAuthority("ADMINISTRATEUR").
                and().authorizeRequests().antMatchers("/utilisateur/password").hasAnyAuthority("UTILISATEUR", "ADMINISTRATEUR").
                and().authorizeRequests().antMatchers("/**/**").hasAuthority("UTILISATEUR").
                and().authorizeRequests().anyRequest().authenticated().
                and().formLogin().loginPage("/login").permitAll().
                and().logout().deleteCookies("JSESSIONID").
                and().rememberMe().key("secret").
                and().sessionManagement(session -> {
            session.maximumSessions(1);
        });
        return http.build();


    }


    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
