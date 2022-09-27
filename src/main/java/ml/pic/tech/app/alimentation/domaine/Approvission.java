package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Approvission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String statutApprovission;
    private int quantite;
    private double montantTotal;
    private double montantPaye;
    private double montantRestant;

    @ManyToMany
    private List<Produit> produits;
    @ManyToOne
    private Personne personne;
    @ManyToOne
    private User user;
}