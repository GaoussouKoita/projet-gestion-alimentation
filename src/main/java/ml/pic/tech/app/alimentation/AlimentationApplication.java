package ml.pic.tech.app.alimentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlimentationApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);


    public static void main(String[] args) {
        LOGGER.info("Demarrage de l'application");
        SpringApplication.run(AlimentationApplication.class, args);
    }
}
