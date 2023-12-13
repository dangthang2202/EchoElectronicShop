package echo.tdtu.electronicshopenduser.Controller;

import echo.tdtu.internal.Model.Categories;
import echo.tdtu.internal.Repository.CategoriesRepository;
import echo.tdtu.internal.Repository.DetailProductRepository;
import echo.tdtu.internal.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    DetailProductRepository detailProductRepository;
    @Autowired
    CategoriesRepository categoriesRepository;
    @Autowired
    ProductRepository productRepository;
    @GetMapping("/trang-chu")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username",authentication);
        model.addAttribute("categories",categoriesRepository.findAll());
        model.addAttribute("products",detailProductRepository.findAll());
        return "index";
    }
    @GetMapping("/find-categories/{id}")
    public String findBYCategoriesID(@PathVariable("id") int id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username",authentication);
        Categories categories = categoriesRepository.findById(id).get();
        model.addAttribute("category",categories);
        model.addAttribute("categories",categoriesRepository.findAll());
        return "find-by-category";
    }
}
