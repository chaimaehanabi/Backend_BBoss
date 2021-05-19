package backend_bboss.demo. Models;
import backend_bboss.demo.Models.Roles;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Data
@Getter
@Entity
@Table(	name = "users",  uniqueConstraints = { @UniqueConstraint(columnNames = "username") })
public class Users  implements Serializable  {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String firstname;
    private String password;
    @Email
    private String  Email;
    @Size(max = 50)
    private String tel;
    @Size(max = 50)
    private String Address;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),  inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

    public Users() {
    }

    public String getfirstname() {
        return firstname;
    }

    public void setfirstname(String fullname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(String username, String password, String email) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Users(@NotNull long id, String username, String firstname, String password, @javax.validation.constraints.Email String email, @Size(max = 50) String tel, @Size(max = 50) String address, Set<Roles> roles) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.password = password;
        Email = email;
        this.tel = tel;
        Address = address;
        this.roles = roles;
    }
}