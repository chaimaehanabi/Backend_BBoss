package backend_bboss.demo.Services;

import backend_bboss.demo.Models.Category;
import backend_bboss.demo.Models.Product;

import java.util.List;

public interface ProduitService {

    Product saveProduit( Product p);
    Product updateProduit( Product p);
    void deleteProduit( Product p);
    void deleteProduitById(Long id);
    Product getProduit(Long id);
    List< Product> getAllProduits();
}
