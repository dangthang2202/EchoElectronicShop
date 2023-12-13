package echo.tdtu.internal.Repository;

import echo.tdtu.internal.Model.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<Pay,Integer> {
}

