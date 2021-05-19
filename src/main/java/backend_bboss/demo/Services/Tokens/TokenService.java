package backend_bboss.demo.Services.Tokens;
import backend_bboss.demo.Request.LoginRequest;
import backend_bboss.demo.Security.JWT.JwtProperties;
import backend_bboss.demo.Services.UserDetailsImpl;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Date;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
@Service
public class TokenService {
    private AuthenticationManager authenticationManager;

    @Autowired
    public TokenService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    private String generateToken(Authentication authResult) {

        // Grab principal
        UserDetailsImpl principal = (UserDetailsImpl) authResult.getPrincipal();
        System.out.println(principal.getUsername());

        // Create JWT Token
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
        return token;
    }

    public LoginRequest login(LoginRequest jwtLogin) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtLogin.getUsername(),
                jwtLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = generateToken(authenticate);
        return new LoginRequest (jwtLogin.getUsername(),token);
    }
}
