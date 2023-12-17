package Teme_sping.Online_shop.services;

import Teme_sping.Online_shop.Exceptions.ResourceNotFoundException;
import Teme_sping.Online_shop.dtos.ProductRequestDTO;
import Teme_sping.Online_shop.entities.Category;
import Teme_sping.Online_shop.entities.Product;
import Teme_sping.Online_shop.repositories.CategoryRepository;
import Teme_sping.Online_shop.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Product addProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("category not found"));
        Product productToBeSaved = new Product();
        productToBeSaved.setName(productRequestDTO.getName());
        productToBeSaved.setPrice(productRequestDTO.getPrice());
        productToBeSaved.setCategory(category);
        if(productToBeSaved.getStock()==null){
            productToBeSaved.setStock(1);
        }else {
            productToBeSaved.setStock(productToBeSaved.getStock() + 1);
        }
        return productRepository.save(productToBeSaved);
    }

}
