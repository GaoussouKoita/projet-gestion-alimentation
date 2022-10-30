package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Veuillez inserer le nom du produit svp !")
    @Column(unique = true)
    private String nom;
    @Min(value = 1, message = "Veuillez inserer le prix du produit svp !")
    private double prix;
    private String typeEngros;
    private int nombreEngros;
    @Lob
    private byte[] image;
    @Min(value = 1, message = "Veuillez inserer le prix engros du produit >= 1 svp !")
    private double prixEngros;
    @NotNull(message = "Veuillez inserer le code barre du produit svp !")
    @Column(unique = true)
    private Long codeBarre1;
    private Long codeBarre2;

    @ManyToOne
    private Categorie categorie;

}
