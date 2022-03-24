package com.mystore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mystore.entity.Category;
import com.mystore.entity.Product;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer>{

	boolean existsByName(String name);
	Optional<Category> findByName(String name);
}
