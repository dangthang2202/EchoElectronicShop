package echo.tdtu.electronicshop.controllers;

import echo.tdtu.internal.Model.Voucher;
import echo.tdtu.internal.Repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/voucher")
public class VoucherController {
    @Autowired
    VoucherRepository voucherRepository;

    @GetMapping("")
    public String home(Model model) {
        List<Voucher> vouchers = voucherRepository.findAll();
        model.addAttribute("vouchers",vouchers);
        return "voucher/index";
    }
    @GetMapping("/create")
    public String Create( Model model){
        model.addAttribute("voucher",new Voucher());
        return "voucher/create";
    }

    @PostMapping("/create")
    public String Create(@ModelAttribute("voucher") Voucher voucher){
        voucherRepository.save(voucher);
        return "redirect:/voucher";
    }

    @GetMapping("/delete/{id}")
    public String DeleteByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("voucher",voucherRepository.findById(ID));
        return "voucher/delete";
    }

    @PostMapping("/delete")
    public String DeleteByID(@ModelAttribute("voucher") Voucher voucher){
        voucherRepository.deleteById(voucher.getId());
        return "redirect:/voucher";
    }
    @GetMapping("/edit/{id}")
    public String EditByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("voucher",voucherRepository.findById(ID));
        return "voucher/edit";
    }

    @PostMapping("/edit")
    public String EditByID(@ModelAttribute("voucher") Voucher voucher){
        voucherRepository.save(voucher);
        return "redirect:/voucher";
    }
}