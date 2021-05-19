package backend_bboss.demo.Controllers;
import backend_bboss.demo.Domaine.MessageResponse;
import backend_bboss.demo.Models.ERole;
import backend_bboss.demo.Models.Roles;
import backend_bboss.demo.Models.Users;
import backend_bboss.demo.Repository.RoleRepository;
import backend_bboss.demo.Repository.UserRepository;
import backend_bboss.demo.Request.LoginRequest;
import backend_bboss.demo.Request.RegisterRequest;
import backend_bboss.demo.Services.Tokens.TokenService;
import backend_bboss.demo.Services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
//@Api(value=" users Management System", description="Operations pertaining to users in users Management System")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/BBBos/")
public class UsersController {
    private TokenService tokenService;
    private UserDetailsImpl userService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository repository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    public UsersController(TokenService tokenService,UserDetailsImpl userService, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder; }
        // http://localhost:4200"/BBBos/signin
    @PostMapping("/signin")
    public LoginRequest logIn(@RequestBody LoginRequest jwtLogin){
        return tokenService.login(jwtLogin);
    }
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> createUser(@RequestBody RegisterRequest RegisterRequest){
        Users user = new Users();
        user.setEmail(RegisterRequest.getUsername());
        user.setPassword(passwordEncoder.encode(RegisterRequest.getPassword()));
        user.setActive(1);
        Set<String> strRoles = RegisterRequest.getRoles();
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
        repository.save(user);
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


