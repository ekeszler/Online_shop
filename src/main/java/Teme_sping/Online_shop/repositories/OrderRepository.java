package Teme_sping.Online_shop.repositories;

import jakarta.persistence.criteria.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository {
    List<Order> findAllByUser_IdOrderByCreatedAt(Long userId);
}
