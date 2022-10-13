package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Veuillez inserer une date svp !")
    private LocalDateTime date = LocalDateTime.now();

    private String action;
    @Column(length = Integer.MAX_VALUE)
    private String object;


    @ManyToOne
    private Utilisateur utilisateur;

    public Audit(String action, String object) {
        this();
        this.action = action;
        this.object = object;
    }
}
