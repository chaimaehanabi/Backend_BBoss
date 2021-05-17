package backend_bboss.demo. Repository;
import backend_bboss.demo.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdactRepository extends JpaRepository<Product, Long> {
}
