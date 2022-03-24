package com.mystore.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mystore.entity.Category;
import com.mystore.entity.Product;
import com.mystore.repository.CategoryRepo;

@Service
@Transactional
public class CategoryService {

	@Autowired
	CategoryRepo categoryRepo;
	
    public void  save(Category category){
    	categoryRepo.save(category);
    }
	
    public List<Category> listar(){
        return categoryRepo.findAll();
    }
    
    public Optional<Category> getOne(int id){
        return categoryRepo.findById(id);
    }
    
    public Optional<Category> getByName(String name){
        return categoryRepo.findByName(name);
    }

    public void delete(int id){
    	categoryRepo.deleteById(id);
    }

    public boolean existsById(int id){
        return categoryRepo.existsById(id);
    }

    public boolean existsByName(String name){
        return categoryRepo.existsByName(name);
    }
}
