package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransfertStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Veuillez inserer une date svp !")
    private Date date;
    @NotNull(message = "Veuillez inserer la quantite du produit svp !")
    private int quantite;
    @ManyToOne
    private Produit produit;
    @ManyToOne
    private Magasin magasinDepart;
    @ManyToOne
    private Magasin magasinDestination;
    @ManyToOne
    private User user;
}
