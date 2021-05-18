package backend_bboss.demo.Domaine;

import lombok.*;

import java.util.List;
@EqualsAndHashCode
@Setter
@Data
@AllArgsConstructor
@Getter
public class JwtResponse {
	private String token;
	private String type = "Bearer";

	public JwtResponse(String accessToken) {
		this.token = accessToken;
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
}