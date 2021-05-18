package backend_bboss.demo.Controllers;
import backend_bboss.demo.Models.Users;
import backend_bboss.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
//@Api(value=" users Management System", description="Operations pertaining to users in users Management System")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authenticate")
public class UsersController {
    @Autowired
    UserRepository repository;
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


