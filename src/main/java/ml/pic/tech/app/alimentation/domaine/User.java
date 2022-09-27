package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String login;
    private String password;
    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<Commande> commandes;
    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<Depense> depenses;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public User() {

        roles = new ArrayList<>();
    }

}
