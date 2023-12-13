package echo.tdtu.electronicshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {
    @GetMapping("/trang-chu")
    public String home(Model model) {
        if(model.getAttribute("username") == null || model.getAttribute("password") == null ){
            return "login";
        }
        return "index";
    }
}
