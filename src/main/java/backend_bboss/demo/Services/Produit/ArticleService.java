package backend_bboss.demo.Services.Produit;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import backend_bboss.demo.Dto.ListArticle;
import backend_bboss.demo.Models.Product;
import backend_bboss.demo.Repository.ProdactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ArticleService {
	 @Autowired
	 ProdactRepository repository;
	 
		public List<ListArticle> getAll() {
			System.out.println("Get all Articles 11111...");
	    	return repository.listArticle();	    	
	    }
		
		
		
	    public Optional<Product> findByCode(String code) {
	        return repository.findByCode(code);
	    }
	    
	    public long save(Product article) {
	    	
	        return repository.save(article)
	                             .getId();
	    }
	    public void update(String code, Product article) {
	        Optional<Product> artic = repository.findByCode(code);
	        if (artic.isPresent()) {
				Product art = artic.get();
	            art.setLibelle(article.getLibelle());
		        art.setCcateg(article.getCcateg());
		        repository.save(art);
	        }
	    }
	  
	
	    public List<Product> findByLibelle(String libelle) {
	        return repository.findAllByLibelleContaining(libelle);
	    }

	    public void delete(String code) {
	        Optional<Product> art = repository.findByCode(code);
	        art.ifPresent(repository::delete);
	    }
		
	    public List<Product> findByCcateg(String code) {
	        return repository.findAllByCcateg(code);
	    }

	    public List<Product> findByCscateg(String code) {
	        return repository.findAllByCscateg(code);
	    }

		public int nbre(String code) {
			return repository.nbre(code);
		}



		public int max(String code) {
			return repository.max(code);
		}



		public Optional<Product> findById(Long id) {
			  return repository.findById(id);
		}



		public List<ListArticle> listArtf(int code) {
			// TODO Auto-generated method stub
			return repository.listArticleFour(code);
		}



		



	
}
