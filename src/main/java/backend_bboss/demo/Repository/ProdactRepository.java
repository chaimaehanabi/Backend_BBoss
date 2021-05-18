package backend_bboss.demo. Repository;
import backend_bboss.demo.Models.Category;
import backend_bboss.demo.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdactRepository extends JpaRepository<Product, Long> {


}
