package backend_bboss.demo.Repository;
import backend_bboss.demo.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCode(String code);

    List<Category> findAllByLibelleContaining(String libelle);
    @Query(value = "SELECT count(code) FROM Category ")
    public int nbre();

    @Query(value = "SELECT max(code) FROM Category ")
    public int max();


}
