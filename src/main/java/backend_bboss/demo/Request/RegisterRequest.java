package backend_bboss.demo.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Setter
@Data
@AllArgsConstructor
@Getter
public class RegisterRequest {
    private String username;
	private Set<String> roles;
    private String password;
    private String firstname;
    private String  Email;
    private String tel;
    private String Address;

}
