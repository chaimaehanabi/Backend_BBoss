package backend_bboss.demo.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Data
@AllArgsConstructor
@Getter
public class LoginRequest {
	@NotBlank
	@Size(min=3, max = 60)
	private String username;

	@NotBlank
	@Size(min= 6,max = 40)
	private String password;

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
}
