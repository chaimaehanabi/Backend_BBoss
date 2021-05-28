package backend_bboss.demo. Models;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Data
@Getter
@Entity
@Table(	name = "users",  uniqueConstraints = { @UniqueConstraint(columnNames = "username") })
public class Users  implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String username;
    private String firstname;
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    @Email
    private String Email;
    @Size(max = 50)
    private String tel;
    @Size(max = 50)
    private String Address;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

    public Users(@NotBlank String username, String firstname, @NotBlank @Size(min = 6, max = 20) String password, @javax.validation.constraints.Email String email, @Size(max = 50) String tel) {
        this.username = username;
        this.firstname = firstname;
        this.password = password;
        this. Email = email;
        this.tel = tel;
    }

    public Users(@NotBlank String username, @NotBlank @Size(min = 6, max = 20) String password) {
        this.username = username;
        this.password = password;
    }

    public Users(@NotBlank String username, String firstname, @javax.validation.constraints.Email String email) {
        this.username = username;
        this.firstname = firstname;
        Email = email;
    }
}