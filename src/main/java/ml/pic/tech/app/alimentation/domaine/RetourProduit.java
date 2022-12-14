package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class RetourProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date = LocalDate.now();
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime heure = LocalTime.now();
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
