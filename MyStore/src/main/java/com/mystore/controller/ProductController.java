package com.mystore.controller;


import java.util.List;

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
import com.mystore.entity.Product;
import com.mystore.service.CategoryService;
import com.mystore.service.ProductService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("mystore")
@CrossOrigin
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
		
	@ApiOperation("Guardar producto de un json")
	@PostMapping("/guardarProduct")
    public ResponseEntity<?> create(@RequestBody Product product){
        if(StringUtils.isBlank(product.getTitle()))
            return new ResponseEntity(new Mensaje("el titulo es obligatorio"), HttpStatus.BAD_REQUEST);
        if(product.getPrice()==null || product.getPrice()<0 )
            return new ResponseEntity(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        if(productService.existsByTitle(product.getTitle()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(product.getImages().length < 0)
            return new ResponseEntity(new Mensaje("debe agregar una imagen"), HttpStatus.BAD_REQUEST);
        productService.save(product);
        return new ResponseEntity(new Mensaje("producto creado"), HttpStatus.OK);
    }
	
	@ApiOperation("Visualizar listado de productos")
	@GetMapping("/listarProduct/{limit}/{offset}")
	public ResponseEntity<List<Product>> listarPage(@PathVariable("limit") Integer limit,@PathVariable("offset") Integer offset){
		List<Product> list = productService.listarPage(limit,offset);
		return new ResponseEntity(list, HttpStatus.OK);
	}		

	@ApiOperation("Listar producto dado un ID")
	@GetMapping("/detalleProduct/{id}")
	public ResponseEntity<Product> getById(@PathVariable("id") Integer id){
		if(!productService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Product product = productService.getOne(id).get();
        return new ResponseEntity(product, HttpStatus.OK);
	}   
	
	@ApiOperation("Listar producto dado un ID")
	@GetMapping("/listarProductCat/{id}")
	public ResponseEntity<List<Product>> getByCategory(@PathVariable("id") Integer id){
		if(!categoryService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe esta catagoria"), HttpStatus.NOT_FOUND);
		List<Product> product = productService.findByCategoryId(id);
        return new ResponseEntity(product, HttpStatus.OK);
	}  
	
	@ApiOperation("Eliminar producto dado un ID")
	@DeleteMapping("/eliminarProduct/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id")int id){
        if(!productService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        productService.delete(id);
        return new ResponseEntity(new Mensaje("producto eliminado"), HttpStatus.OK);
    }
	
	@ApiOperation("Actualizar producto mediante un json")
	@PutMapping("/actualizarProduct")
	   public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody Product product){
        if(!productService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if(productService.existsByTitle(product.getTitle()) && productService.getByTitle(product.getTitle()).get().getId() != id)
            return new ResponseEntity(new Mensaje("ese titulo ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(product.getTitle()))
            return new ResponseEntity(new Mensaje("el titulo es obligatorio"), HttpStatus.BAD_REQUEST);
        if(product.getPrice()==null || product.getPrice()<0 )
            return new ResponseEntity(new Mensaje("el precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);

        Product prod = productService.getOne(id).get();
        prod.setTitle(product.getTitle());
        prod.setPrice(product.getPrice());
        productService.save(prod);
        return new ResponseEntity(new Mensaje("producto actualizado"), HttpStatus.OK);
    }

}
