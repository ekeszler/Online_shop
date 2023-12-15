package Teme_sping.Online_shop.controllers;

import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    //Plasam o comanda pentru un utilizator (cu produsele pe care le are in cosul de cumparaturi)
    //
    //Endpoint: /orders/add/{userId}
    @PostMapping("/add/{userId}")
    public ResponseEntity<Order> addOrderByUser(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.addOrderToUser(userId));

    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> viewAllOrderByUser(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.viewOrders(userId));
    }
}
