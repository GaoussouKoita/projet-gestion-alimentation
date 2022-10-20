package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Veuillez inserer une date svp !")
    private LocalDateTime date = LocalDateTime.now();
    private double montant;
    private double remise;
    private String typeVente;

    @ManyToOne
    private Utilisateur user;
    @ManyToOne
    private Magasin magasin;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<IO_Produits> io_produits = new ArrayList<>();

}
