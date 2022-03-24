package com.mystore.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.commons.lang3.StringUtils;

import com.mystore.dto.Mensaje;
import com.mystore.entity.Category;
import com.mystore.service.CategoryService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("mystore")
@CrossOrigin
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@ApiOperation("Guardar categoria de un json")
	@PostMapping("/guardarCategory")
    public ResponseEntity<?> create(@RequestBody Category category){
        if(StringUtils.isBlank(category.getName()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        categoryService.save(category);
        return new ResponseEntity(new Mensaje("producto creado"), HttpStatus.OK);
    }
	
	@ApiOperation("Visualizar listado de categorias")
	@GetMapping("/listarCategory")
	public ResponseEntity<List<Category>> listar(){
		List<Category> list = categoryService.listar();
		return new ResponseEntity(list, HttpStatus.OK);
	}
	
	@ApiOperation("Listar categoria dado un ID")
	@GetMapping("/listarCategId/{id}")
	public ResponseEntity<Category> getById(@PathVariable("id") Integer id){
		if(!categoryService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Category category = categoryService.getOne(id).get();
        return new ResponseEntity(category, HttpStatus.OK);
	}
	
	@ApiOperation("Eliminar categoria dado un ID")
	@DeleteMapping("/eliminarCategory/{id}")
	public ResponseEntity<?> deleteCat(@PathVariable("id")int id){
        if(!categoryService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        categoryService.delete(id);
        return new ResponseEntity(new Mensaje("producto eliminado"), HttpStatus.OK);
    }
	
	@ApiOperation("Actualizar usuario mediante un json")
	@PutMapping("/actualizarCategory")
	   public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody Category category){
        if(!categoryService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if(categoryService.existsByName(category.getName()) && categoryService.getByName(category.getName()).get().getId() != id)
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(category.getName()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        Category cat = categoryService.getOne(id).get();
        cat.setName(cat.getName());
        categoryService.save(cat);
        return new ResponseEntity(new Mensaje("categoria actualizada"), HttpStatus.OK);
    }
}
