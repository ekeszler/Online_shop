package Teme_sping.Online_shop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("wishlistitem-product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    @JsonBackReference("wishlistitem-wishlist")
    private WhishList whishList;

    public WishlistItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public WhishList getWhishList() {
        return whishList;
    }

    public void setWhishList(WhishList whishList) {
        this.whishList = whishList;
    }
}
