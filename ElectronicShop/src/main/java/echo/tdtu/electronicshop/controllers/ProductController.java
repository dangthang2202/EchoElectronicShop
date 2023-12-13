package echo.tdtu.electronicshop.controllers;

import echo.tdtu.internal.Model.Product;
import echo.tdtu.internal.Repository.CategoriesRepository;
import echo.tdtu.internal.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoriesRepository categoriesRepository;
    @GetMapping("")
    public String home(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("products",productList);
        return "product/index";
    }

    @GetMapping("/create")
    public String Create( Model model){
        model.addAttribute("categories",categoriesRepository.findAll());
        model.addAttribute("product",new Product());
        return "product/create";
    }

    @PostMapping("/create")
    public String Create(@ModelAttribute("product") Product product){
        product.setImage("/img/"+product.getImage());
        productRepository.save(product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String DeleteByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("categories",categoriesRepository.findAll());
        model.addAttribute("product",productRepository.findById(ID));
        return "product/delete";
    }

    @PostMapping("/delete")
    public String DeleteByID(@ModelAttribute("product") Product product){
        productRepository.deleteById(product.getId());
        return "redirect:/product";
    }

    @GetMapping("/edit/{id}")
    public String EditByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("categories",categoriesRepository.findAll());
        model.addAttribute("product",productRepository.findById(ID));
        return "product/edit";
    }

    @PostMapping("/edit")
    public String EditByID(@ModelAttribute("product") Product product){
        product.setImage("/img/"+product.getImage());
        productRepository.save(product);
        return "redirect:/product";
    }
}