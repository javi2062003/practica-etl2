// Fichero: src/main/java/com/practica/mdm2/service/ProductService.java
package com.practica.mdm2.mdm2.etl.service;

import com.practica.mdm2.mdm2.etl.model.ProductoFinal;
import com.practica.mdm2.mdm2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductoFinal> getAllProductoFinales() {
        return productRepository.findAll();
    }

    public Optional<ProductoFinal> getProductoById(Long id) {
        return productRepository.findById(id);
    }
}