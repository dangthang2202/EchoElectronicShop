package echo.tdtu.internal.Repository;

import echo.tdtu.internal.Model.DetailProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailProductRepository extends JpaRepository<DetailProduct,Integer> {
        List<DetailProduct> findAllByProductId(int id);
        DetailProduct findById(int id);
}
