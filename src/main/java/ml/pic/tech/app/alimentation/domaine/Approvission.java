package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
    @NotNull(message = "Veuillez inserer une date svp !")
    private Date date;
    private String statutApprovission;
    private double montantTotal;
    private double montantPaye;
    private double montantRestant;

    @ManyToMany
    private List<IO_Produits> io_produits = new ArrayList<>();

    @ManyToOne
    private Personne personne;
    @ManyToOne
    private User user;
    @ManyToOne
    private Magasin magasin;
}