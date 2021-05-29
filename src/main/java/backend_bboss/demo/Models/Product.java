package backend_bboss.demo.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String code;
    private String libelle;
    private double pa;
    private double pv;
    private int tva;
    private int stock;
    private String ccateg;
    private String cscateg;
    private String fileName;
    private int codef;;
    @JsonBackReference(value = "category")
    @ManyToOne
    private Category category;
    @JsonBackReference(value = "Fournisseur")
    @ManyToOne
    private Fournisseur four;
}
