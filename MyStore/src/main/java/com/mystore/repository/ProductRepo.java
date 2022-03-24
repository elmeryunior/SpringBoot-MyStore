package com.mystore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mystore.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{
	
	//modificar la consulta para recibir un paginado
	@Query(nativeQuery = true, value = "SELECT * FROM `product` LIMIT :limit OFFSET :offset ")
	List<Product> listarPage(@Param("limit") Integer limit,@Param("offset") Integer offset);
	
	Optional<Product> findByTitle(String title);
	List<Product> findByCategoryId(Integer categoryId);
	boolean existsByTitle(String title);
	boolean existsByDescription(String description);
}