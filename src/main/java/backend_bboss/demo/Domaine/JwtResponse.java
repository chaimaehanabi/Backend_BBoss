package backend_bboss.demo.Domaine;

import lombok.*;

import java.util.List;
@EqualsAndHashCode
@Setter
@Data
@AllArgsConstructor
@Getter
public class JwtResponse {
	private Long id;
	private String token;
	private String type = "Bearer";
	private String email;
	private String username;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

	public JwtResponse(Long id, String email, String username, List<String> roles) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
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

	public List<String> getRoles() {
		return roles;
	}
}