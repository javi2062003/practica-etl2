// Fichero: src/main/java/com/practica/mdm2/repository/ProductRepository.java
package com.practica.mdm2.mdm2.repository;

import com.practica.mdm2.mdm2.etl.model.ProductoFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductoFinal, Long> {

}