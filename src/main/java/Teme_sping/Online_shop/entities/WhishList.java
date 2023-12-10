package Teme_sping.Online_shop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class WhishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "wishList", cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JsonManagedReference("wishlistitem-wishlist")
    private List<WishlistItem> wishListItems;


    @OneToOne
            @JsonBackReference("user-wishlist")
    @JoinColumn(name = "user_id")
    private User user;

    public WhishList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<WishlistItem> getWishListItems() {
        return wishListItems;
    }

    public void setWishListItems(List<WishlistItem> wishListItems) {
        this.wishListItems = wishListItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
