package backend_bboss.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Data
@Getter
@Entity

@Table(name = "fournisseur",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code"
                ),
                @UniqueConstraint(columnNames = "email")
        })
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int code;
    @NotBlank
    @Size(max = 60)
    private String libelle;
    private String responsable;
    private String adresse;
    private String ville;
    private String tel;
    @NotBlank
    @Size(max = 60)
    @Email
    private String email;
    private String fax;
    private String pwd;
    private String  matfisc;
    private double  soldef;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "four")
    @JsonIgnore
    private List<Product> products;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public String getResponsable() {
        return responsable;
    }
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setSoldef(float soldef) {
        this.soldef = soldef;
    }





}