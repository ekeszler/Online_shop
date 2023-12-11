package Teme_sping.Online_shop.repositories;

import Teme_sping.Online_shop.entities.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListItemRepository extends JpaRepository<WishlistItem, Long> {
}
