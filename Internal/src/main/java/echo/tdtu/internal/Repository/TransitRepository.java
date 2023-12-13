package echo.tdtu.internal.Repository;

import echo.tdtu.internal.Model.Transit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransitRepository extends JpaRepository<Transit,Integer> {
}
