package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class RetourProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Veuillez inserer une date svp !")
    private LocalDateTime date = LocalDateTime.now();
    private String motif;
    private String typeRetour;
    @Min(value = 1, message = "La quantite ne peut etre nulle")
    private int quantite;
    @Min(value = 5,message = "Veuillez inserer le prix par unite")
    private double prix;

    @ManyToOne
    private Personne personne;
    @ManyToOne
    private Produit produit;
    @ManyToOne
    private Vente vente;
    @ManyToOne
    private Magasin magasin;
    @ManyToOne
    private Utilisateur user;
}
