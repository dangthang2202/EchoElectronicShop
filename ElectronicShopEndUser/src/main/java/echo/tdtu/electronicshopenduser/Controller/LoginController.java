package echo.tdtu.electronicshopenduser.Controller;

import echo.tdtu.internal.DTO.UserDTO;
import echo.tdtu.internal.Model.User;
import echo.tdtu.internal.Repository.OrderRepository;
import echo.tdtu.internal.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("userDTO",new User());
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("userDTO",new UserDTO());
        return "register";
    }
    @PostMapping("/register")
    public String register( @ModelAttribute("userDTO") UserDTO userDTO,
                           BindingResult result,
                           Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("userDTO", userDTO);
                return "register";
            }
            String username = userDTO.getUsername();
            User user = userRepository.findByUserName(username);
            if (user != null) {
                model.addAttribute("userDTO", userDTO);
                model.addAttribute("errors", "Your username has been registered!");
                return "register";
            }
            else if(userRepository.findByEmail(userDTO.getEmail()) != null){
                model.addAttribute("userDTO", userDTO);
                model.addAttribute("errors", "Your email already exists!!");
                return "register";
            }
            else if (userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
                userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                User user1 = new User();
                user1.setPassWord(userDTO.getPassword());
                user1.setUserName(userDTO.getUsername());
                userRepository.save(user1);
                model.addAttribute("success", "Register successfully! Please login");
                model.addAttribute("userDTO", userDTO);
            }
            else {
                model.addAttribute("userDTO", userDTO);
                model.addAttribute("errors", "Your password maybe wrong! Check again!");
                return "register";
            }
        } catch (Exception e) {
            model.addAttribute("errors", "The server has been wrong!");
        }
        return "register";
    }

}
