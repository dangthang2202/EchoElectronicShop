package echo.tdtu.electronicshop.controllers;

import echo.tdtu.internal.Model.DetailProduct;
import echo.tdtu.internal.Repository.DetailProductRepository;
import echo.tdtu.internal.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/detailProduct")
public class DetailProductController {
    @Autowired
    DetailProductRepository detailProductRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/{id}")
    public String home(@PathVariable("id") int id, Model model) {
        List<DetailProduct> detailProductList = detailProductRepository.findAllByProductId(id);
        model.addAttribute("idProduct",id);
        model.addAttribute("detailProducts",detailProductList);
        return "detailProduct/index";
    }

    @GetMapping("/create/{id}")
    public String Create(@PathVariable("id") int id, Model model){
        model.addAttribute("idProduct",id);
        model.addAttribute("detailProduct",new DetailProduct());
        return "detailProduct/create";
    }

    @PostMapping("/create/{id}")
    public String Create(@PathVariable("id") int id,@ModelAttribute("detailProduct") DetailProduct detailProduct,Model model){
        if(detailProduct.checkEmpty()){
            model.addAttribute("idProduct",id);
            model.addAttribute("detailProduct",detailProduct);
            model.addAttribute("message","Wrong when write input!");
            return "detailProduct/create";
        }
        detailProduct.setProduct(productRepository.findById(id));
        detailProductRepository.save(detailProduct);
        return "redirect:/detailProduct/"+id;
    }

    @GetMapping("/delete/{id}")
    public String DeleteByID(@PathVariable("id") int ID,Model model){
        DetailProduct detailProduct = detailProductRepository.findById(ID);
        model.addAttribute("idProduct",detailProduct.getProduct().getId());
        model.addAttribute("detailProduct",detailProduct);
        return "detailProduct/delete";
    }

    @PostMapping("/delete/{id}")
    public String DeleteByID(@PathVariable("id") int ID,@ModelAttribute("detailProduct") DetailProduct detailProduct){
        detailProductRepository.deleteById(detailProduct.getId());
        return "redirect:/detailProduct/"+ID;
    }

    @GetMapping("/edit/{id}")
    public String EditByID(@PathVariable("id") int ID,Model model){
        DetailProduct detailProduct = detailProductRepository.findById(ID);
        model.addAttribute("idProduct",detailProduct.getProduct().getId());
        model.addAttribute("detailProduct",detailProduct);
        return "detailProduct/edit";
    }

    @PostMapping("/edit/{id}")
    public String EditByID(@PathVariable("id") int id,@ModelAttribute("detailProduct") DetailProduct detailProduct,Model model){
        if(detailProduct.checkEmpty()){
            model.addAttribute("idProduct",id);
            model.addAttribute("detailProduct",detailProduct);
            model.addAttribute("message","Wrong when write input!");
            return "detailProduct/edit";
        }
        detailProduct.setProduct(productRepository.findById(id));
        detailProductRepository.save(detailProduct);
        return "redirect:/detailProduct/"+id;
    }
}