package Teme_sping.Online_shop.dtos;

public class WishListRequestDTO {

    private Long productId;
    private Long userId;

    public WishListRequestDTO(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
