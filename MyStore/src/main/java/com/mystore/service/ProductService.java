package com.mystore.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mystore.entity.Product;
import com.mystore.repository.ProductRepo;

@Service
@Transactional
public class ProductService {

	@Autowired
	ProductRepo productRepo;
	
    public void  save(Product product){
    	productRepo.save(product);
    }
	
    public List<Product> listarPage(Integer limit,Integer offset){
        return productRepo.listarPage(limit, offset);
    }
    
    public List<Product> listarPageCat(Integer categId,Integer limit,Integer offset){
        return productRepo.listarPageCat(categId,limit, offset);
    }
    
    public Optional<Product> getOne(int id){
        return productRepo.findById(id);
    }

    public Optional<Product> getByTitle(String title){
        return productRepo.findByTitle(title);
    }
    public List<Product> findByCategoryId(Integer categoryId){
    	return productRepo.findByCategoryId(categoryId);
    }
    
    public void delete(int id){
    	productRepo.deleteById(id);
    }

    public boolean existsById(int id){
        return productRepo.existsById(id);
    }

    public boolean existsByTitle(String title){
        return productRepo.existsByTitle(title);
    }
    
    public boolean existsByDescription(String description){
        return productRepo.existsByDescription(description);
    }
}
