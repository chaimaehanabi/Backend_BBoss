package backend_bboss.demo. Repository;
import backend_bboss.demo.Dto.ListArticle;
import backend_bboss.demo.Models.Category;
import backend_bboss.demo.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProdactRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(String code);
    List<Product> findAllByLibelleContaining(String libelle);

    List<Product> findAllByCcateg(String code);

    List<Product>findAllByCscateg(String code);
    @Query(value = "SELECT new backend_bboss.demo.Dto.ListArticle(a.id,a.code,a.libelle,b.libelle,c.libelle,a.pa,a.pv,a.tva,a.stock,d.libelle,d.code)" + " from Product a, Scategorie b, Category c, Fournisseur d  where a.cscateg = b.code and b.ccateg = c.code and a.codef = d.code")
    List<ListArticle> listArticle();

    @Query(value = "SELECT new backend_bboss.demo.Dto.ListArticle (a.id,a.code,a.libelle,b.libelle,c.libelle,a.pa,a.pv,a.tva,a.stock,d.libelle,d.code)"
            + " from Product a, Scategorie b, Category c, Fournisseur d  where a.cscateg = b.code and b.ccateg = c.code and a.codef = d.code"
            + " and  d.code  = :code")
    List<ListArticle> listArticleFour(@Param("code") int  code);

    @Query(value = "SELECT max(code) FROM Product   WHERE cscateg  = :code")
    public int nbre (@Param("code") String  code);

    @Query(value = "SELECT max(code) FROM Product where cscateg = :code")
    public int max(@Param("code") String  code);

    Optional<Product> findAllById(Long id);
}
