package Teme_sping.Online_shop.controllers;

import Teme_sping.Online_shop.dtos.WishListRequestDTO;
import Teme_sping.Online_shop.entities.CartItem;
import Teme_sping.Online_shop.entities.WishList;
import Teme_sping.Online_shop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartItem> addToWishList(@RequestBody WishListRequestDTO wishlistRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(wishListService.addItemToWishList(wishlistRequestDTO));
    }
}
