package com.practica.mdm2.mdm2.controller;

import com.practica.mdm2.mdm2.etl.model.ProductoFinal;
import com.practica.mdm2.mdm2.etl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products") // Ruta base para todos los métodos de este controlador
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController (ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductoFinal> getAllProducts() {
        return productService.getAllProductoFinales();
    }


     @GetMapping("/{id}")
    public ResponseEntity<ProductoFinal> getProductById(@PathVariable Long id) {
        Optional<ProductoFinal> product = productService.getProductoById(id);

        // Usamos ResponseEntity para tener control sobre el código de estado HTTP
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get()); // Devuelve 200 OK con el producto
        } else {
            return ResponseEntity.notFound().build(); // Devuelve 404 Not Found
        }
    }
}