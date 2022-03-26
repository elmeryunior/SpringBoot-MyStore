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
            return new ResponseEntity(new Mensaje("The title is required"), HttpStatus.BAD_REQUEST);
        if(product.getPrice()==null || product.getPrice()<0 )
            return new ResponseEntity(new Mensaje("Price must be greater than 0"), HttpStatus.BAD_REQUEST);
        if(productService.existsByTitle(product.getTitle()))
            return new ResponseEntity(new Mensaje("That name already exists"), HttpStatus.BAD_REQUEST);
        if(product.getImages().length < 0)
            return new ResponseEntity(new Mensaje("You must add an image"), HttpStatus.BAD_REQUEST);
        productService.save(product);
        return new ResponseEntity(new Mensaje("Producto created"), HttpStatus.OK);
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
            return new ResponseEntity(new Mensaje("No exist"), HttpStatus.NOT_FOUND);
        Product product = productService.getOne(id).get();
        return new ResponseEntity(product, HttpStatus.OK);
	}   
	
	@ApiOperation("Listar producto dado un ID")
	@GetMapping("/listarProductCat/{id}/{limit}/{offset}")
	public ResponseEntity<List<Product>> getByCategory(@PathVariable("id") Integer id,@PathVariable("limit") Integer limit,@PathVariable("offset") Integer offset){
		if(!categoryService.existsById(id))
            return new ResponseEntity(new Mensaje("This category does not exist"), HttpStatus.NOT_FOUND);
		List<Product> product = productService.listarPageCat(id, limit, offset);
        return new ResponseEntity(product, HttpStatus.OK);
	}  
	
	@ApiOperation("Eliminar producto dado un ID")
	@DeleteMapping("/eliminarProduct/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id")Integer id){
        if(!productService.existsById(id))
            return new ResponseEntity(new Mensaje("No exist"), HttpStatus.NOT_FOUND);
        productService.delete(id);
        return new ResponseEntity(new Mensaje("Producto deleted"), HttpStatus.OK);
    }
	
	@ApiOperation("Actualizar producto mediante un json")
	@PutMapping("/actualizarProduct/{id}")
	   public ResponseEntity<?> update(@PathVariable("id")Integer id, @RequestBody Product product){
        if(!productService.existsById(id))
            return new ResponseEntity(new Mensaje("No exist"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(product.getTitle()))
            return new ResponseEntity(new Mensaje("The title is required"), HttpStatus.BAD_REQUEST);
        if(product.getPrice()==null || product.getPrice()<=0 )
            return new ResponseEntity(new Mensaje("Price must be greater than 0"), HttpStatus.BAD_REQUEST);

        Product prod = productService.getOne(id).get();
        prod.setTitle(product.getTitle());
        prod.setPrice(product.getPrice());
        prod.setImages(product.getImages());
        prod.setDescription(product.getDescription());
        prod.setCategoryId(product.getCategoryId());
        productService.save(prod);
        return new ResponseEntity(new Mensaje("Producto updated"), HttpStatus.OK);
    }

}
