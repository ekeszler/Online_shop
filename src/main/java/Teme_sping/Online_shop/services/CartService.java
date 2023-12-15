package Teme_sping.Online_shop.services;

import Teme_sping.Online_shop.Exceptions.ResourceNotFoundException;
import Teme_sping.Online_shop.dtos.CartItemResponseDTO;
import Teme_sping.Online_shop.dtos.CartRequestDTO;
import Teme_sping.Online_shop.dtos.CartResponseDTO;
import Teme_sping.Online_shop.entities.CartItem;
import Teme_sping.Online_shop.entities.Product;
import Teme_sping.Online_shop.entities.User;
import Teme_sping.Online_shop.repositories.CartItemRepository;
import Teme_sping.Online_shop.repositories.ProductRepository;
import Teme_sping.Online_shop.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private CartItemRepository cartItemRepository;

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private CartItemMapper cartItemMapper;

    @Autowired
    public CartService(CartItemRepository cartItemRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.CartItemMapper = cartItemMapper;
    }

    @Transactional
    public CartItem addToCart(CartRequestDTO cartRequestDTO) {
        User user = userRepository.findById(cartRequestDTO.getUserId()).orElseThrow(()-> new ResourceNotFoundException("user not found"));
        Product product = productRepository.findById(cartRequestDTO.getProductId()).orElseThrow(()-> new ResourceNotFoundException("product not found"));
        if(product.getStock()<cartRequestDTO.getQuantity()){
            throw new ResourceNotFoundException("out of stock");
        }

        CartItem cartItem = cartItemMapper.mapCartRequestDTOToCartItem(cartRequestDTO,product,user);
//        CartItem cartItem = new CartItem();
//        cartItem.setProduct(product);
//        cartItem.setUser(user);
//        cartItem.setQuantity(cartRequestDTO.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    public CartResponseDTO viewCart(Long userId){
        List<CartItem> cartItems = cartItemRepository.findAllByUser_Id(userId);
        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setTotalPrice(computeTotalPrice(cartItems));
        cartResponseDTO.setCartItemResponseDTOS(getCartItemResponseDTOS(cartItems));
        return cartResponseDTO;
    }

    private List<CartItemResponseDTO> getCartItemResponseDTOS(List<CartItem> cartItems) {
        List<CartItemResponseDTO> cartItemResponseDTOS = cartItems.stream()
                .map(cartItem -> mapCartItemToCartItemResponseDTO(cartItem))
                .collect(Collectors.toList());
        return cartItemResponseDTOS;
    }

    public Double computeTotalPrice(List<CartItem> cartItems){
        Optional<Double> totalPrice = cartItems.stream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice())
                .reduce((sum, number)->sum+number);
        return totalPrice.orElseThrow(()-> new ResourceNotFoundException("total price could not be computed"));
    }

    public CartItemResponseDTO mapCartItemToCartItemResponseDTO(CartItem cartItem){
        CartItemResponseDTO cartItemResponseDTO = new CartItemResponseDTO();
        cartItemResponseDTO.setId(cartItem.getId());
        cartItemResponseDTO.setProductId(cartItem.getProduct().getId());
        cartItemResponseDTO.setProductName(cartItem.getProduct().getName());
        cartItemResponseDTO.setPrice(cartItem.getProduct().getPrice());
        cartItemResponseDTO.setQuantity(cartItem.getQuantity());
        return cartItemResponseDTO;
    }
}
