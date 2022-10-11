package ml.pic.tech.app.alimentation;

import ml.pic.tech.app.alimentation.securite.entity.Role;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AlimentationApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);


    public static void main(String[] args) {
        LOGGER.info("Demarrage de l'application");
        SpringApplication.run(AlimentationApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AccountService accontService) {
        return args -> {
            Role role1 = new Role(1L, "ADMINISTRATEUR");
            Role role2 = new Role(2L, "UTILISATEUR");
            List<Role> roles = new ArrayList<>();
            roles.add(role1);
            roles.add(role2);
            accontService.addUtilisateur(new Utilisateur(1L, "KOITA", "Gaoussou", "Baguineda",
                    76684788L, "admin", "1234", roles));

            roles.remove(role1);

            accontService.addUtilisateur(new Utilisateur(2L, "BRIBAUD", "Yannick", "Dakar",
                    773332211L, "user", "1234", roles));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
