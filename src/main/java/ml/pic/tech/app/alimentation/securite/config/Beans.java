package ml.pic.tech.app.alimentation.securite.config;

import ml.pic.tech.app.alimentation.securite.entity.Role;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class Beans {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner commandLineRunner(AccountService accontService) {
        return args -> {
            Role role1 = new Role(1L, "ADMINISTRATEUR");
            Role role2 = new Role(2L, "UTILISATEUR");
            List<Role> roles = new ArrayList<>();
            roles.add(role1);
            roles.add(role2);

            for (Role r : roles) {
                accontService.addRole(r);
            }
            accontService.addUtilisateur(new Utilisateur(1L, "KOITA", "Gaoussou", "Baguineda",
                    76684788L, "admin@g", "1234","1234" ,roles));

            roles.remove(role1);
                accontService.addUtilisateur(new Utilisateur(2L, "BRIBAUD", "Yannick", "Dakar",
                        773332211L, "user@g", "1234", "1234",roles));
            for (int i = 3; i < 10; i++) {
                accontService.addUtilisateur(new Utilisateur((long) (i), "BRIBAUD", "Yannick", "Dakar",
                        773332211L, "user@g"+i, "1234", "1234",roles));

            }
        };
    }
}
