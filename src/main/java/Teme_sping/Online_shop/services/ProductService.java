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

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductService(CategoryRepository categoryRepository ,ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Transactional
    public Product addProduct(ProductRequestDTO productRequestDTO){
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("category not found"));
        Product productToBeSeaved = new Product();
        productToBeSeaved.setName(productRequestDTO.getName());
        productToBeSeaved.setPrice(productRequestDTO.getPrice());
        productToBeSeaved.setCategory(category);
        productRepository.save(productToBeSeaved);
        return productRepository.save(productToBeSeaved);
    }

}
