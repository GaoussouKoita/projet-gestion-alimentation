package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IO_Produits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 1, message = "Veuillez inserer la quantite >=1 svp !")
    private int quantite;
    private double prix;
    private String type;
    @ManyToOne
    private Produit produit;

}
