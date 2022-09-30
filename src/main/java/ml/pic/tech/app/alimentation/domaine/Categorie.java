package ml.pic.tech.app.alimentation.domaine;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, message = "Veuillez inserer le nom de la categorie")
    private String nom;

    @ToString.Exclude
    @OneToMany(mappedBy = "categorie")
    private List<Produit> produits;

}
