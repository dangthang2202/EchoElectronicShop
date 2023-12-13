package echo.tdtu.electronicshopenduser.Controller;

import echo.tdtu.internal.DTO.FilterDTO;
import echo.tdtu.internal.Model.DetailProduct;
import echo.tdtu.internal.Repository.CategoriesRepository;
import echo.tdtu.internal.Repository.DetailProductRepository;
import echo.tdtu.internal.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    DetailProductRepository detailProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoriesRepository categoriesRepository;

    @GetMapping("")
    public String getShop(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username",authentication);
        model.addAttribute("categories",categoriesRepository.findAll());
        model.addAttribute("filterDTO",new FilterDTO());
        return "shop";
    }
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable("id")int id,Model model){
        DetailProduct detailProduct = detailProductRepository.findById(id);
        model.addAttribute("products",
                detailProductRepository.findAllByProductId(detailProduct.getProduct().getId()));
        model.addAttribute("detail",detailProduct);
        return "detail";
    }
    @PostMapping("/filter")
    public String filterProduct(@RequestBody FilterDTO filterDTO, Model model){
        List<DetailProduct> detailProducts = detailProductRepository.findAll();
        if(filterDTO.categoryId!=0){
            List<DetailProduct> detailProductByCateId = new ArrayList<>();
            for (DetailProduct dt:detailProducts) {
                if(dt.getProduct().getCategories().getId() == filterDTO.categoryId){
                    detailProductByCateId.add(dt);
                }
            }
            detailProducts = detailProductByCateId;
            model.addAttribute("isCategory",filterDTO.categoryId);
        }
        Stream<DetailProduct> stream = detailProducts.stream();
        if(!filterDTO.color.isEmpty()){
            stream = stream.filter(p->filterDTO.color.contains(String.valueOf(p.getColor())));
        }
        if(!filterDTO.ram.isEmpty()){
            stream = stream.filter(p->filterDTO.ram.contains(String.valueOf(p.getRam())));
        }
        if(!filterDTO.rom.isEmpty()){
            stream = stream.filter(p->filterDTO.rom.contains(String.valueOf(p.getRom())));
        }
        if(!filterDTO.search.isEmpty()){
            stream = stream.filter(p->p.getProduct().getName().toLowerCase().contains(filterDTO.search.toLowerCase()));
        }

        List<DetailProduct> filteredProducts = stream.toList();
        int itemsPerPage = 9;
        int startIndex = (filterDTO.pageNum - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, filteredProducts.size());
        int totalPages = (int) Math.ceil((double) filteredProducts.size() / itemsPerPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("detailProducts",filteredProducts.subList(startIndex, endIndex));
        model.addAttribute("categories",categoriesRepository.findAll());
        return "product";
    }

}
