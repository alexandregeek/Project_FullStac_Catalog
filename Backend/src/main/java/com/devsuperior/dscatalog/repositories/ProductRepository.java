package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Product;
//para registrar com componente injetavel
@Repository 
public interface ProductRepository extends JpaRepository<Product,Long> { // 

}
