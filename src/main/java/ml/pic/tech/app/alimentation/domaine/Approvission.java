package ml.pic.tech.app.alimentation.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Veuillez inserer une date svp !")
    private LocalDateTime date = LocalDateTime.now();
    private String statutApprovission;
    private double montantTotal;
    private double montantPaye;
    private double montantRestant;

    @ManyToOne
    private Personne personne;
    @ManyToOne
    private User user;
    @ManyToOne
    private Magasin magasin;
    @ManyToMany
    private List<IO_Produits> io_produits = new ArrayList<>();
}