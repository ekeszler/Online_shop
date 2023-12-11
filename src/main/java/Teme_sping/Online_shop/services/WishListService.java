package Teme_sping.Online_shop.services;

import Teme_sping.Online_shop.Exceptions.ResourceNotFoundException;
import Teme_sping.Online_shop.dtos.WishListRequestDTO;
import Teme_sping.Online_shop.entities.Product;
import Teme_sping.Online_shop.entities.User;
import Teme_sping.Online_shop.entities.WishList;
import Teme_sping.Online_shop.entities.WishlistItem;
import Teme_sping.Online_shop.repositories.ProductRepository;
import Teme_sping.Online_shop.repositories.UserRepository;
import Teme_sping.Online_shop.repositories.WishListItemRepository;
import Teme_sping.Online_shop.repositories.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private UserRepository userRepository;
    private WishListRepository wishListRepository;
    private WishListItemRepository wishListItemRepository;

    private ProductRepository productRepository;

    @Autowired
    public WishListService(UserRepository userRepository, WishListRepository wishListRepository, WishListItemRepository wishListItemRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.wishListRepository = wishListRepository;
        this.wishListItemRepository = wishListItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public WishList addItemToWishList(WishListRequestDTO wishListRequestDTO){
        User user = userRepository.findById(wishListRequestDTO.getUserId()).orElseThrow(()-> new ResourceNotFoundException("user not found"));
        Product product = productRepository.findById(wishListRequestDTO.getProductId()).orElseThrow(()-> new ResourceNotFoundException("product not found"));
        WishList wishList = user.getWhishList();
        WishlistItem wishlistItem = new WishlistItem();
        wishlistItem.setProduct(product);
        wishlistItem.setWishList(wishList);
         wishList.getWishListItems().add(wishlistItem);
        return wishListRepository.save(wishList);
    }
}
