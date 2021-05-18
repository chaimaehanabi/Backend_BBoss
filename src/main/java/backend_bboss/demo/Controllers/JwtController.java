package backend_bboss.demo.Controllers;
import java.util.*;
import java.util.stream.Collectors;
import backend_bboss.demo.Domaine.JwtResponse;
import backend_bboss.demo.Domaine.MessageResponse;
import backend_bboss.demo.Models.ERole;
import backend_bboss.demo.Models.Roles;
import backend_bboss.demo.Models.Users;
import backend_bboss.demo.Repository.RoleRepository;
import backend_bboss.demo.Repository.UserRepository;
import backend_bboss.demo.Request.LoginRequest;
import backend_bboss.demo.Request.RegisterRequest;
import backend_bboss.demo.Security.JwtUtils;
import backend_bboss.demo.Services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/BBBos")
public class JwtController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	// http://localhost:4200/api/BBBos/login
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println("aaaa");
		System.out.println(loginRequest.getPassword());
		System.out.println(loginRequest.getUsername());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("aaaa");
		System.out.println(loginRequest.getPassword());
		System.out.println(loginRequest.getUsername());
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(),
												 userDetails.getUsername(),
												 roles));
	}
	// http://localhost:4200//register
	@PostMapping("/register")
	public ResponseEntity<?> registerUser( @RequestBody RegisterRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Username is already exist!")); }

	//*	if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				//*return ResponseEntity
					//*				.badRequest().body(new MessageResponse("Error: Email is already in use!"));}

		// Create new user's account
		Users user = new Users(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));
		Set<String> strRoles = signUpRequest.getRoles();
		Set<Roles> roles = new HashSet<>();
		if (strRoles == null) {
			Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Roles adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("Registration successfully!"));
	}

}