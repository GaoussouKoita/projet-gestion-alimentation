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
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, message = "Veuillez entre le nom svp!")
    private String nom;
    @Size(min = 2, message = "Veuillez entre le prenom svp!")
    private String prenom;
    private String adresse;
    private String telephone;
    private String typeCompte;

//    @ToString.Exclude
//    @OneToMany(mappedBy = "personne")
//    private List<DetteClient> detteClients;


}