package Teme_sping.Online_shop.controllers;

import Teme_sping.Online_shop.dtos.ProductRequestDTO;
import Teme_sping.Online_shop.dtos.WishListRequestDTO;
import Teme_sping.Online_shop.entities.Product;
import Teme_sping.Online_shop.entities.WishList;
import Teme_sping.Online_shop.services.ProductService;
import Teme_sping.Online_shop.services.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    private WishListService wishListService;

    @Autowired
    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping
    public ResponseEntity<WishList> addToWishList(@RequestBody WishListRequestDTO wishlistRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(wishListService.addItemToWishList(wishlistRequestDTO));
    }
}
