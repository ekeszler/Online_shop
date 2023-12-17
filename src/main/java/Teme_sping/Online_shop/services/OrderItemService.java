package Teme_sping.Online_shop.services;

import Teme_sping.Online_shop.entities.OrderItem;
import Teme_sping.Online_shop.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    private OrderItemRepository orderitemRepository;


    @Autowired
    public OrderitemService(OrderItemRepository orderitemRepository) {
        this.orderitemRepository = orderitemRepository;
    }
}
