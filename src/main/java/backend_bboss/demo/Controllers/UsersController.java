package backend_bboss.demo.Controllers;
import backend_bboss.demo.Domaine.MessageResponse;
import backend_bboss.demo.Domaine.Request.JwtResponse;
import backend_bboss.demo.Models.ERole;
import backend_bboss.demo.Models.Roles;
import backend_bboss.demo.Models.Users;
import backend_bboss.demo.Repository.RoleRepository;
import backend_bboss.demo.Repository.UserRepository;
import backend_bboss.demo.Request.LoginRequest;
import backend_bboss.demo.Request.RegisterRequest;
import backend_bboss.demo.Security.JWT.JwtUtils;
import backend_bboss.demo.Services.UserDetailsImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
//@Api(value=" users Management System", description="Operations pertaining to users in users Management System")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/BBBos/api/authenticate/")
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
    // http://localhost:4200"/BBBos/login
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }
    // http://localhost:4200"/BBBos/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username is already exist!"));
        }
        // Create new user's account
        val user = new Users(registerRequest.getUsername(), registerRequest.getFirstname(), encoder.encode(registerRequest.getPassword()),registerRequest.getEmail(),registerRequest.getTel());
        Set<String> strRoles = registerRequest.getRoles();
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
        userRepository.findAll().forEach(Utilisateur::add);
        return Utilisateur;
    }

    //Affichafe des utilisateur par id

    //@ApiOperation(value = "View USRES by Id", response = List.class)
    @GetMapping(value="/users/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> getUtilisateurById(@PathVariable(value = "id") Long UtilisateurId)
            throws ResourceNotFoundException {
        Users Utilisateur = userRepository.findById(UtilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found for this id :: " + UtilisateurId));
        return ResponseEntity.ok().body(Utilisateur);
    }

    ///add user
      @PostMapping(path = "/users")
    public Users createUtilisateur(@Valid @RequestBody Users Utilisateur) {
        return userRepository.save(Utilisateur);
    }


    //supprimer un user par son id
    @DeleteMapping("/users/delete/{id}")
    public Map<String, Boolean> deleteUtilisateur(@PathVariable(value = "id") Long UtilisateurId)
            throws ResourceNotFoundException {
        Users Utilisateur = userRepository.findById(UtilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found  id :: " + UtilisateurId));

        userRepository.delete(Utilisateur);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted user "+UtilisateurId, Boolean.TRUE);
        return response;
    }
    //supprimer tous les utilisateurs
    @DeleteMapping("/users/delete")
    public ResponseEntity<String> deleteAllUtilisateur() {
        System.out.println("Delete All Utilisateur...");
        userRepository.deleteAll();
        return new ResponseEntity<>("All Utilisateurs have been deleted!", HttpStatus.OK);
    }

    //update users
    @PutMapping("/users/{id}")
    public ResponseEntity<Users> updateUtilisateur(@PathVariable("id") long id, @RequestBody Users Utilisateur) {
        System.out.println("Update Utilisateur with ID = " + id + "...");
        Optional<Users> UtilisateurInfo = userRepository.findById(id);
        if (UtilisateurInfo.isPresent()) {
            Users utilisateur = UtilisateurInfo.get();
            utilisateur.setUsername(Utilisateur.getUsername());
            utilisateur.setPassword(Utilisateur.getPassword());
            utilisateur.setAddress(Utilisateur.getAddress());
            utilisateur.setTel(Utilisateur.getTel());
            utilisateur.setEmail(Utilisateur.getEmail());
            return new ResponseEntity<>(userRepository.save(Utilisateur), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


