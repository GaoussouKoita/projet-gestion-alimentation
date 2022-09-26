package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double prix;
    private String typeEngros;
    private int nombreEngros;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String  image;
    private double prixEngros;
    private Long codeBarre1;
    private Long codeBarre2;

    @ManyToOne
    private Categorie categorie;

}
