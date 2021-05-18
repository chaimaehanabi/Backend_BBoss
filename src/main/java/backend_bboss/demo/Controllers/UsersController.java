package backend_bboss.demo.Controllers;
import backend_bboss.demo.Domaine.JwtResponse;
import backend_bboss.demo.Domaine.MessageResponse;
import backend_bboss.demo.Models.ERole;
import backend_bboss.demo.Models.Roles;
import backend_bboss.demo.Models.Users;
import backend_bboss.demo.Repository.RoleRepository;
import backend_bboss.demo.Repository.UserRepository;
import backend_bboss.demo.Request.LoginRequest;
import backend_bboss.demo.Request.RegisterRequest;
import backend_bboss.demo.Security.JWT.JwtUtils;
import backend_bboss.demo.Services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

//@Api(value=" users Management System", description="Operations pertaining to users in users Management System")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/BBBos")
public class UsersController {
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
    @Autowired
    UserRepository repository;
    // http://localhost:4200/api/BBBos/login
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("test");
        System.out.println(loginRequest.getPassword());
        System.out.println(loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
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
    //@ApiOperation(value = "View a list of available USRES", response = List.class)

    @GetMapping("/users/ALL")
    public List<Users> getAllUtilisateur() {
        System.out.println("Get all Utilisateur...");
        List<Users> Utilisateur = new ArrayList<>();
        repository.findAll().forEach(Utilisateur::add);
        return Utilisateur;
    }

    //Affichafe des utilisateur par id

    //@ApiOperation(value = "View USRES by Id", response = List.class)
    @GetMapping(value="/users/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> getUtilisateurById(@PathVariable(value = "id") Long UtilisateurId)
            throws ResourceNotFoundException {
        Users Utilisateur = repository.findById(UtilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found for this id :: " + UtilisateurId));
        return ResponseEntity.ok().body(Utilisateur);
    }

    ///add user
      @PostMapping(path = "/users")
    public Users createUtilisateur(@Valid @RequestBody Users Utilisateur) {
        return repository.save(Utilisateur);
    }

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    //supprimer un user par son id
    @DeleteMapping("/users/delete/{id}")
    public Map<String, Boolean> deleteUtilisateur(@PathVariable(value = "id") Long UtilisateurId)
            throws ResourceNotFoundException {
        Users Utilisateur = repository.findById(UtilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found  id :: " + UtilisateurId));

        repository.delete(Utilisateur);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted user "+UtilisateurId, Boolean.TRUE);
        return response;
    }

    //supprimer tous les utilisateurs
    @DeleteMapping("/users/delete")
    public ResponseEntity<String> deleteAllUtilisateur() {
        System.out.println("Delete All Utilisateur...");
        repository.deleteAll();
        return new ResponseEntity<>("All Utilisateurs have been deleted!", HttpStatus.OK);
    }

    //update users
    @PutMapping("/users/{id}")
    public ResponseEntity<Users> updateUtilisateur(@PathVariable("id") long id, @RequestBody Users Utilisateur) {
        System.out.println("Update Utilisateur with ID = " + id + "...");
        Optional<Users> UtilisateurInfo = repository.findById(id);

        if (UtilisateurInfo.isPresent()) {
            Users utilisateur = UtilisateurInfo.get();
            utilisateur.setUsername(Utilisateur.getUsername());
            utilisateur.setPassword(Utilisateur.getPassword());
            utilisateur.setEmail(Utilisateur.getEmail());
            return new ResponseEntity<>(repository.save(Utilisateur), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


