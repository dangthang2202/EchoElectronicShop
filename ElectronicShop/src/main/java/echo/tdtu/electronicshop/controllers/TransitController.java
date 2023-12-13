package echo.tdtu.electronicshop.controllers;

import echo.tdtu.internal.Model.Transit;
import echo.tdtu.internal.Repository.TransitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transit")
public class TransitController {
    @Autowired
    TransitRepository transitRepository;

    @GetMapping("")
    public String home(Model model) {
        List<Transit> transitList = transitRepository.findAll();
        model.addAttribute("transits",transitList);
        return "transit/index";
    }
    @GetMapping("/create")
    public String Create( Model model){
        model.addAttribute("transit",new Transit());
        return "transit/create";
    }

    @PostMapping("/create")
    public String Create(@ModelAttribute("transit") Transit transit){
        transitRepository.save(transit);
        return "redirect:/transit";
    }

    @GetMapping("/delete/{id}")
    public String DeleteByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("transit",transitRepository.findById(ID));
        return "transit/delete";
    }

    @PostMapping("/delete")
    public String DeleteByID(@ModelAttribute("transit") Transit transit){
        transitRepository.deleteById(transit.getId());
        return "redirect:/transit";
    }
    @GetMapping("/edit/{id}")
    public String EditByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("transit",transitRepository.findById(ID));
        return "transit/edit";
    }

    @PostMapping("/edit")
    public String EditByID(@ModelAttribute("transit") Transit transit){
        transitRepository.save(transit);
        return "redirect:/transit";
    }
}