package echo.tdtu.electronicshop.controllers;

import echo.tdtu.internal.Model.User;
import echo.tdtu.internal.Repository.CategoriesRepository;
import echo.tdtu.internal.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public String home(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("users",userList);
        return "user/index";
    }
    @GetMapping("/create")
    public String Create( Model model){
        model.addAttribute("user",new User());
        return "user/create";
    }

    @PostMapping("/create")
    public String Create(@ModelAttribute("user") User user){
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/delete/{id}")
    public String DeleteByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("user",userRepository.findById(ID));
        return "user/delete";
    }

    @PostMapping("/delete")
    public String DeleteByID(@ModelAttribute("user") User user){
        userRepository.deleteById(user.getId());
        return "redirect:/user";
    }
    @GetMapping("/edit/{id}")
    public String EditByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("user",userRepository.findById(ID));
        return "user/edit";
    }

    @PostMapping("/edit")
    public String EditByID(@ModelAttribute("user") User user){
        userRepository.save(user);
        return "redirect:/user";
    }
}