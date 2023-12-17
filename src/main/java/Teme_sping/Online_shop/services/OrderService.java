package Teme_sping.Online_shop.services;

import Teme_sping.Online_shop.Exceptions.ResourceNotFoundException;
import Teme_sping.Online_shop.dtos.CartItemResponseDTO;
import Teme_sping.Online_shop.dtos.CartResponseDTO;
import Teme_sping.Online_shop.dtos.OrderItemResponseDTO;
import Teme_sping.Online_shop.dtos.OrderResponseDTO;
import Teme_sping.Online_shop.entities.CartItem;
import Teme_sping.Online_shop.entities.Order;
import Teme_sping.Online_shop.entities.OrderItem;
import Teme_sping.Online_shop.entities.User;
import Teme_sping.Online_shop.repositories.CartItemRepository;
import Teme_sping.Online_shop.repositories.OrderRepository;
import Teme_sping.Online_shop.repositories.OrderItemRepository;
import Teme_sping.Online_shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Teme_sping.Online_shop.entities.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    private UserRepository userRepository;

    private CartItemRepository cartItemRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;

    }

    public OrderItemResponseDTO mapFromCartitemDTOToOrderitemDTO(CartItemResponseDTO cartItem) {
        OrderItemResponseDTO orderItemDTO = new OrderItemResponseDTO();
        orderItemDTO.setId(cartItem.getId());
        orderItemDTO.setPrice(cartItem.getPrice());
        orderItemDTO.setQuantity(cartItem.getQuantity());
        orderItemDTO.setProductName(cartItem.getProductName());
        return orderItemDTO;
    }

    public Double computeTotalPrice(List<OrderItem> orderitems) {
        Optional<Double> totalPrice = orderitems.stream()
                .map(orderitem -> orderitem.getPrice())
                .reduce((sum, number) -> sum + number);

        return totalPrice.orElseThrow(() -> new ResourceNotFoundException("total price could not be computed"));

    }

    public OrderItem mapFromCartitemtoOrderitem(CartItem cartItem, Order order) {
        OrderItem orderitem = new OrderItem();
        orderitem.setQuantity(cartItem.getQuantity());
        orderitem.setProduct(cartItem.getProduct());
        orderitem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        orderitem.setOrder(order);
        return orderitem;
    }

    //Plasam o comanda pentru un utilizator (cu produsele pe care le are in cosul de cumparaturi)
    //
    //Endpoint: /orders/add/{userId}
    @Transactional
    public Order addOrderToUser(Long userId) {
        //gasesc userul dupa id
        //creez o noua comanda
        //atasez comanda de utilizator
        //gasesc cartitem-urile utilizatorului dupa id
        //mut cartitem-urile in orderitem , sau le mapez
        // atasez lista de orderitem la order
        //salvez orderul
        //sterg lista de cartitem-uri dupa id utilizator
        //salvez user-ul

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Order order = new Order();

        List<CartItem> cartItems = cartItemRepository.findAllByUser_Id(userId);
        if (cartItems.size()==0){
            throw new ResourceNotFoundException("order cannot be placed. Cart is empty");
        }
        List<OrderItem> orderitems = cartItems.stream()
                .map(cartItem -> mapFromCartitemtoOrderitem(cartItem,order))
                .collect(Collectors.toList());
        order.setTotalPrice(computeTotalPrice(orderitems));
        order.setOrderItems(orderitems);
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        cartItemRepository.deleteAllByUser_Id(userId);
        return orderRepository.save(order);
    }

    @Transactional
    public List<Order> viewOrders(Long userId) {
        List<Order> allOrders = orderRepository.findAllByUser_IdOrderByCreatedAt(userId);
        return allOrders;
    }

}
