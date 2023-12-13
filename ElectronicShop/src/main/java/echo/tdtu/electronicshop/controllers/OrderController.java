package echo.tdtu.electronicshop.controllers;

import echo.tdtu.internal.Model.Order;
import echo.tdtu.internal.Model.OrderDetail;
import echo.tdtu.internal.Model.User;
import echo.tdtu.internal.Repository.OrderDetailRepository;
import echo.tdtu.internal.Repository.OrderRepository;
import echo.tdtu.internal.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;
    @GetMapping("order")
    public String homeOrder(Model model) {
        List<Order> orderList = orderRepository.findAll();
        model.addAttribute("orders",orderList);
        return "order/index";
    }

    @GetMapping("orderDetail/{id}")
    public String homeOrderDetail(@PathVariable("id") int id, Model model) {
        List<OrderDetail> orderDetails = orderDetailRepository.findAllById(id);
        model.addAttribute("orderDetails",orderDetails);
        return "order/detail";
    }

    @GetMapping("order/edit/{id}")
    public String EditByID(@PathVariable("id") int ID,Model model){
        model.addAttribute("order",orderRepository.findById(ID));
        return "order/edit";
    }

    @PostMapping("order/edit")
    public String EditByID(@ModelAttribute("order") Order order){
        User user = userRepository.findById(order.getUser().getId());
        order.setUser(user);
        orderRepository.save(order);
        return "redirect:/order";
    }
}