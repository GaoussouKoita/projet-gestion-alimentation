package ml.pic.tech.app.alimentation;

import ml.pic.tech.app.alimentation.domaine.Role;
import ml.pic.tech.app.alimentation.domaine.User;
import ml.pic.tech.app.alimentation.securite.AccontService;
import ml.pic.tech.app.alimentation.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
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
    CommandLineRunner commandLineRunner(AccontService accontService){
        return args -> {
            Role role1=new Role(1L, "ADMINISTRATEUR");
            Role role2=new Role(2L, "UTILISATEUR");
            List<Role> roles = new ArrayList<>();
            roles.add(role1);
            roles.add(role2);
            accontService.addRole(role1);
            accontService.addRole(role2);
            accontService.addUser(new User(1L, "KOITA", "Gaoussou", "Baguineda",
                    76684788L,"admin", "1234", roles));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    ;
}
