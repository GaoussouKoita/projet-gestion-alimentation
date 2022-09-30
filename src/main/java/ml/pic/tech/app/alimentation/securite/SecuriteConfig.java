package ml.pic.tech.app.alimentation.securite;

import ml.pic.tech.app.alimentation.domaine.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecuriteConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AccontService service;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                ml.pic.tech.app.alimentation.domaine.User user = service.findByLogin(username);
                List<Role> roles = user.getRoles();
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                roles.forEach(r -> {
                    authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
                });
                return new User(user.getLogin(), user.getPassword(), authorities);
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

      http.formLogin().loginPage("/login").permitAll();
        http.logout().permitAll();
        http.authorizeRequests().antMatchers("/user/**").hasAuthority("ADMINISTRATEUR");
        http.authorizeRequests().antMatchers("/login", "/css/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();


//       http.authorizeRequests().anyRequest().permitAll();
    }

}
