package com.exemple.jaumesoap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemple.jaumesoap.model.ProductModel;



@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Integer>{
	
	ProductModel findByName(String name);
}
