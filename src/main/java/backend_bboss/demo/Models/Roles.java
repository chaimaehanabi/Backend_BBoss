package backend_bboss.demo.Models;

import backend_bboss.demo.Models.ERole;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
@Setter
@Data
@AllArgsConstructor
@Getter
@Table
@Entity
public class Roles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ERole name;
    String nane_roles;
    public Roles() {
        super();
    }
}
