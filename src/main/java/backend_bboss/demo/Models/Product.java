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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private int quantity;
    private String photo;
    private Date dateCreation;
    @JsonBackReference(value = "category")
    @ManyToOne
    private Category category;
}
