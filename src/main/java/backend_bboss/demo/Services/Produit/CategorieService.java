package backend_bboss.demo.Services.Produit;

import java.util.List;
import java.util.Optional;

import backend_bboss.demo.Models.Category;
import backend_bboss.demo.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
@Service
@Transactional
public class CategorieService {
	 @Autowired
	 CategoryRepository repository;
	 
		public List<Category> getAll() {
			System.out.println("Get all Categories 11111...");
	    	return repository.findAll(Sort.by("libelle").ascending());	    	
	    }
		
		public int max() {
			return repository.max();
		}
		
		public int nbre() {
			return repository.nbre();
		}
		
	    public Optional<Category> findByCode(String code) {
	        return repository.findByCode(code);
	    }
	    
	    public long save(Category categorie) {
	    	System.out.println("save  all Categories 11111...");
	        Category cat = new Category ();
	        cat.setCode(categorie.getCode());
	        cat.setLibelle(categorie.getLibelle());
	        return repository.save(cat).getIdCat();
	    }
	    public void update(String code, Category categorie) {
	        Optional<Category> categ = repository.findByCode(code);
	        if (categ.isPresent()) {
				Category cat = categ.get();
	            cat.setLibelle(categorie.getLibelle());
	            repository.save(cat);
	        }
	    }
	  
	
	    public List<Category> findByLibelle(String libelle) {
	        return repository.findAllByLibelleContaining(libelle);
	    }

	    public void delete(String code) {
	        Optional<Category> cat = repository.findByCode(code);
	        cat.ifPresent(repository::delete);
	    }
		
}
