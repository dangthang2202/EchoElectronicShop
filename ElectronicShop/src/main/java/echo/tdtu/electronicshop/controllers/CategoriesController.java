package echo.tdtu.electronicshop.controllers;

import echo.tdtu.internal.Model.Categories;
import echo.tdtu.internal.Repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoriesController {
    @Autowired
    CategoriesRepository categoriesRepository;

    @GetMapping("")
    public String home(Model model) {
        List<Categories> categoriesList = categoriesRepository.findAll();
        model.addAttribute("categoriesList",categoriesList);
        return "categories/index";
    }
    @GetMapping("/create")
    public String Create( Model model){
        model.addAttribute("category",new Categories());
        return "categories/create";
    }

    @PostMapping("/create")
    public String Create(@ModelAttribute("category") Categories category){
        categoriesRepository.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String DeleteByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("category",categoriesRepository.findById(ID));
        return "categories/delete";
    }

    @PostMapping("/delete")
    public String DeleteByID(@ModelAttribute("category") Categories category){
        categoriesRepository.deleteById(category.getId());
        return "redirect:/categories";
    }
    @GetMapping("/edit/{id}")
    public String EditByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("category",categoriesRepository.findById(ID));
        return "categories/edit";
    }

    @PostMapping("/edit")
    public String EditByID(@ModelAttribute("category") Categories category,Model model){
        if(category.checkEmpty()){
            model.addAttribute("category",category);
            model.addAttribute("message","Wrong to edit!!");
            return "redirect:/categories/edit/"+ category.getId();
        }
        categoriesRepository.save(category);
        return "redirect:/categories";
    }
}