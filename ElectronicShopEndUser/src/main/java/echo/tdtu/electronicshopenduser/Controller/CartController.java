package echo.tdtu.electronicshopenduser.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import echo.tdtu.electronicshopenduser.services.RedisService;
import echo.tdtu.internal.DTO.CartDTO;
import echo.tdtu.internal.DTO.SummaryDTO;
import echo.tdtu.internal.Repository.CategoriesRepository;
import echo.tdtu.internal.Repository.DetailProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    DetailProductRepository detailProductRepository;

    @Autowired
    CategoriesRepository categoriesRepository;
    
    @Autowired
    RedisService session;

    @GetMapping("")
    public String cart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("categories",categoriesRepository.findAll());
        model.addAttribute("username",authentication);
        return "cart";
    }
    @GetMapping("/update")
    public String updateCart(Model model, @RequestParam("id") int id, @RequestParam("quantity") int quantity) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (session.getAttribute(authentication.getName())== null){
            List<CartDTO> cartDTOList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            String cartJson = objectMapper.writeValueAsString(cartDTOList);
            session.setAttribute(authentication.getName(),cartJson);
            SummaryDTO summaryDTO = new SummaryDTO();
            model.addAttribute("summary",summaryDTO);
            model.addAttribute("cartDTOList",session.getAttribute(authentication.getName()));
            model.addAttribute("categories",categoriesRepository.findAll());
            return "cart-items";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<CartDTO> cartDTOList = objectMapper.readValue(session.getAttribute(authentication.getName()), new TypeReference<>() {
        });
        SummaryDTO summaryDTO = new SummaryDTO();
        long totalPrice = 0;
        for (CartDTO cartDTO: cartDTOList) {
            if(id!=0 && quantity!=0){
                if(id==cartDTO.getProductId()){
                    cartDTO.setQuantity(quantity);
                }
            }
            totalPrice += cartDTO.getQuantity()*cartDTO.getPrice();
        }
        String cartJson = objectMapper.writeValueAsString(cartDTOList);
        if(id!=0 && quantity!=0){
            session.setAttribute(authentication.getName(),cartJson);
        }
        summaryDTO.setPrice(totalPrice);
        model.addAttribute("summary",summaryDTO);
        model.addAttribute("cartDTOList",cartDTOList);
        model.addAttribute("categories",categoriesRepository.findAll());
        return "cart-items";
    }
    @GetMapping("/delete/{id}")
    public String deleteCart(Model model, @PathVariable("id") int id) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (session.getAttribute(authentication.getName())== null){
            List<CartDTO> cartDTOList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            String cartJson = objectMapper.writeValueAsString(cartDTOList);
            session.setAttribute(authentication.getName(),cartJson);
            SummaryDTO summaryDTO = new SummaryDTO();
            model.addAttribute("summary",summaryDTO);
            model.addAttribute("cartDTOList",session.getAttribute(authentication.getName()));
            model.addAttribute("categories",categoriesRepository.findAll());
            return "cart-items";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<CartDTO> cartDTOList = objectMapper.readValue(session.getAttribute(authentication.getName()), new TypeReference<>() {
        });

        SummaryDTO summaryDTO = new SummaryDTO();
        long totalPrice = 0;
        for (CartDTO cartDTO: cartDTOList) {
            if(cartDTO.getProductId()==id){
                cartDTOList.remove(cartDTO);
                if (cartDTOList.isEmpty()){
                    cartDTOList = new ArrayList<>();
                    break;
                }
                continue;
            }
            totalPrice += cartDTO.getQuantity()*cartDTO.getPrice();
        }
        String cartJson = objectMapper.writeValueAsString(cartDTOList);
        session.setAttribute(authentication.getName(),cartJson);

        summaryDTO.setPrice(totalPrice);
        model.addAttribute("summary",summaryDTO);
        model.addAttribute("cartDTOList",cartDTOList);
        model.addAttribute("categories",categoriesRepository.findAll());
        return "cart-items";
    }

    @GetMapping("/add-to-cart/{id}")
    public ResponseEntity<String> addToCart(@PathVariable("id") int id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (session.getAttribute(authentication.getName())!= null){
                ObjectMapper objectMapper = new ObjectMapper();
                List<CartDTO> cartDTOList = objectMapper.readValue(session.getAttribute(authentication.getName()), new TypeReference<>() {
                });
                for (CartDTO cartDTO: cartDTOList) {
                    if(cartDTO.getProductId() == id){
                        cartDTO.setQuantity(cartDTO.getQuantity()+1);
                        objectMapper = new ObjectMapper();
                        String cartJson = objectMapper.writeValueAsString(cartDTOList);
                        session.setAttribute(authentication.getName(), cartJson);
                        return ResponseEntity.ok(cartJson);
                    }
                }
                CartDTO cartDTO = new CartDTO();
                cartDTO.transfer(detailProductRepository.findById(id), 1);
                cartDTOList.add(cartDTO);
                objectMapper = new ObjectMapper();
                String cartJson = objectMapper.writeValueAsString(cartDTOList);
                session.setAttribute(authentication.getName(), cartJson);
                return ResponseEntity.ok(cartJson);
            }

            CartDTO cartDTO = new CartDTO();
            cartDTO.transfer(detailProductRepository.findById(id), 1);
            List<CartDTO> cartDTOList = new ArrayList<>();
            cartDTOList.add(cartDTO);
            ObjectMapper objectMapper = new ObjectMapper();
            String cartJson = objectMapper.writeValueAsString(cartDTOList);
            session.setAttribute(authentication.getName(), cartJson);
            return ResponseEntity.ok(cartJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
        }
    }
}
