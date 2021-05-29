package backend_bboss.demo.Services.Produit;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import backend_bboss.demo.Dto.ListCategorie;
import backend_bboss.demo.Models.Scategorie;
import backend_bboss.demo.Repository.ScategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ScategorieService {
	 @Autowired
	 ScategorieRepository repository;
	 
		public List<ListCategorie> getAll() {
			System.out.println("Get all Sous Categories 11111...");
	    	return repository.listScateg();	 
	    }
		
	    public Optional<Scategorie> findByCode(String code) {
	        return repository.findByCode(code);
	    }
	    
	    public long save(Scategorie scategorie) {
	    	System.out.println("save  all Categories 11111...");
	        Scategorie scat = new Scategorie();
	        scat.setCode(scategorie.getCode());
	        scat.setLibelle(scategorie.getLibelle());
	        scat.setCcateg(scategorie.getCcateg());
	        scat.setRang(1);
	        return repository.save(scat)
	                             .getId();
	    }
	    public void update(String code, Scategorie scategorie) {
	        Optional<Scategorie> scateg = repository.findByCode(code);
	        if (scateg.isPresent()) {
	            Scategorie scat = scateg.get();
	            scat.setLibelle(scategorie.getLibelle());
	            scat.setCcateg(scategorie.getCcateg());
	            repository.save(scat);
	        }
	    }
	  
	
	    public List<Scategorie> findByLibelle(String libelle) {
	        return repository.findAllByLibelleContaining(libelle);
	    }

	    public void delete(String code) {
	        Optional<Scategorie> cat = repository.findByCode(code);
	        cat.ifPresent(repository::delete);
	    }
		
	    public List<Scategorie> findByCcateg(String code) {
	        return repository.findAllByCcateg(code);
	    }



		public int nbre(String code) {
			return repository.nbre(code);
		}



		public int max(String code) {
			return repository.max(code);
		}



		



	
}
