package backend_bboss.demo.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@AllArgsConstructor
@Setter
@Data
@Getter
@Entity
@Table(name = "products")
public class Product {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private int quantity;
    private String photo;

    @JsonBackReference(value = "category")
    @ManyToOne
    private Category category;
    public Product() {
        super();
    }
}
