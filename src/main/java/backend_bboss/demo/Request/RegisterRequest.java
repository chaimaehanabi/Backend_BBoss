package backend_bboss.demo.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

@Setter
@Data
@AllArgsConstructor
@Getter
public class RegisterRequest {
	@NotBlank
    @Size(min = 3, max = 40)
    private String username;
    @NotBlank
	private Set<String> roles;
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

}
