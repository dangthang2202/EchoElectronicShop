package echo.tdtu.electronicshopenduser.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import echo.tdtu.electronicshopenduser.services.RedisService;
import echo.tdtu.internal.DTO.CartDTO;
import echo.tdtu.internal.DTO.SummaryDTO;
import echo.tdtu.internal.Model.*;
import echo.tdtu.internal.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/check-out")
public class CheckOutController {
    @Autowired
    RedisService session;

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DetailProductRepository detailProductRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @GetMapping("")
    public String checkout(Model model) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (session.getAttribute(authentication.getName()) == null){
            List<CartDTO> cartDTOList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            String cartJson = objectMapper.writeValueAsString(cartDTOList);
            session.setAttribute(authentication.getName(),cartJson);
            SummaryDTO summaryDTO = new SummaryDTO();
            model.addAttribute("summary",summaryDTO);
            model.addAttribute("cartDTOList",session.getAttribute(authentication.getName()));
            model.addAttribute("categories",categoriesRepository.findAll());
            model.addAttribute("username",authentication);
            return "checkout";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<CartDTO> cartDTOList = objectMapper.readValue(session.getAttribute(authentication.getName()), new TypeReference<>() {
        });
        SummaryDTO summaryDTO = new SummaryDTO();
        long totalPrice = 0;
        for (CartDTO cartDTO: cartDTOList) {
            totalPrice += cartDTO.getQuantity()*cartDTO.getPrice();
        }
        summaryDTO.setPrice(totalPrice);
        model.addAttribute("user",userRepository.findByUserName(authentication.getName()));
        model.addAttribute("summary",summaryDTO);
        model.addAttribute("cartDTOList",cartDTOList);
        model.addAttribute("categories",categoriesRepository.findAll());
        model.addAttribute("username",authentication);

        return "checkout";
    }

    @PostMapping("/update")
    public String EditByID(@ModelAttribute("user") User user){
        userRepository.save(user);
        return "redirect:/check-out";
    }
    public Order saveOrder(User user,long totalPrice,int totalQuantity,int status){
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(totalPrice);
        order.setTotalQuantity(totalQuantity);
        order.setStatus(status);
        return order;
    }

    public OrderDetail saveOrderDetail(int quantity, Order order, DetailProduct product){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setDetailProduct(product);
        orderDetail.setQuantity(quantity);
        return orderDetail;
    }
    @GetMapping("/order")
    public String order() throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        ObjectMapper objectMapper = new ObjectMapper();
        List<CartDTO> cartDTOList = objectMapper.readValue(session.getAttribute(authentication.getName()), new TypeReference<>() {
        });

        long totalPrice = 0;
        int totalQuantity = 0;
        for (CartDTO cartDTO: cartDTOList) {
            totalQuantity+=cartDTO.getQuantity();
            totalPrice += cartDTO.getQuantity()*cartDTO.getPrice();
        }

        Order order = saveOrder(userRepository.findByUserName(authentication.getName())
                ,totalPrice,totalQuantity,1);
        orderRepository.save(order);
        for (CartDTO cartDTO: cartDTOList) {
            OrderDetail orderDetail = saveOrderDetail(cartDTO.getQuantity()
                    ,order
                    ,detailProductRepository.findById(cartDTO.getProductId()));
            orderDetailRepository.save(orderDetail);
        }
        cartDTOList = new ArrayList<>();
        String cartJson = objectMapper.writeValueAsString(cartDTOList);
        session.setAttribute(authentication.getName(),cartJson);

        return "redirect:/check-out";
    }
}