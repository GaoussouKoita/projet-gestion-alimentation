package ml.pic.tech.app.alimentation.securite.config;

import ml.pic.tech.app.alimentation.securite.entity.Role;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.
                authorizeRequests().antMatchers("/login", "/error", "/user/password", "/css/**", "/js/**", "/fonts/**").permitAll().
                and().authorizeRequests().antMatchers("/user/**").hasAuthority("ADMINISTRATEUR").
                and().authorizeRequests().antMatchers("/user/password").hasAnyAuthority("UTILISATEUR" ,"ADMINISTRATEUR").
                and().authorizeRequests().antMatchers("/**/**").hasAuthority("UTILISATEUR").
                and().logout().permitAll().
                and().formLogin().loginPage("/login").permitAll().
                and().authorizeRequests().anyRequest().authenticated().
                 and().sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
//                and().authorizeRequests().anyRequest().permitAll().
//                and().csrf().disable().build();
        return http.build();


    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                Utilisateur utilisateur = service.findByLogin(username);
                List<Role> roles = utilisateur.getRoles();
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                roles.forEach(r -> {
                    authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
                });
                return new User(utilisateur.getLogin(), utilisateur.getPassword(), authorities);
            }
        });
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
}
