package echo.tdtu.internal.DTO;

import echo.tdtu.internal.Model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String firstName;

    private String lastName;

    private String username;

    private String email;
    private String phoneNumber;
    private String address;
    private String password;

    private String repeatPassword;

    private String messageOrder;

    public UserDTO transfer(User user){
        this.firstName = user.getName();
        this.username = user.getUserName();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.password = user.getPassWord();
        return this;
    }
}
