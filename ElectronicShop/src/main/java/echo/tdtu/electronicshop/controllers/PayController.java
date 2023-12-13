package echo.tdtu.electronicshop.controllers;

import echo.tdtu.internal.Model.Pay;
import echo.tdtu.internal.Repository.PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    PayRepository payRepository;

    @GetMapping("")
    public String home(Model model) {
        List<Pay> payList = payRepository.findAll();
        model.addAttribute("pays",payList);
        return "pay/index";
    }
    @GetMapping("/create")
    public String Create( Model model){
        model.addAttribute("pay",new Pay());
        return "pay/create";
    }

    @PostMapping("/create")
    public String Create(@ModelAttribute("pay") Pay pay){
        payRepository.save(pay);
        return "redirect:/pay";
    }

    @GetMapping("/delete/{id}")
    public String DeleteByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("pay",payRepository.findById(ID));
        return "pay/delete";
    }

    @PostMapping("/delete")
    public String DeleteByID(@ModelAttribute("pay") Pay pay){
        payRepository.deleteById(pay.getId());
        return "redirect:/pay";
    }
    @GetMapping("/edit/{id}")
    public String EditByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("pay",payRepository.findById(ID));
        return "pay/edit";
    }

    @PostMapping("/edit")
    public String EditByID(@ModelAttribute("pay") Pay pay){
        payRepository.save(pay);
        return "redirect:/pay";
    }
}