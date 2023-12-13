package echo.tdtu.internal.Repository;

import echo.tdtu.internal.DTO.UserDTO;
import echo.tdtu.internal.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findById(int id);
    User findByUserName(String username);
    User findByEmail(String email);
    UserDTO save(UserDTO userDTO);
}
