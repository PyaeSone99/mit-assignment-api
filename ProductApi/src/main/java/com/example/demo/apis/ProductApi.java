package com.example.demo.apis;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.dto.AddProductForm;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.dto.UpdateProductForm;
import com.example.demo.model.entity.Product;
import com.example.demo.model.services.ProductServices;




@RestController
@RequestMapping("product")
public class ProductApi {
	
	@Autowired
	private ProductServices productServices;
	
	@PostMapping(path = "saveProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductDto create(@RequestParam("code")String code,@RequestParam("name")String name,
							 @RequestParam("description")String description,@RequestParam("image")MultipartFile file,
							 @RequestParam("price")double price,@RequestParam("quantity")int quantity)throws IOException {
		AddProductForm form = new AddProductForm(code, name, description, file, price, quantity);
		return productServices.create(form);
	}
	
	@GetMapping()
	public Page<List<ProductDto>> findAll(@RequestParam("searchKey")Optional<String> searchKey,
			 						@RequestParam(required = false,defaultValue = "0")int current,
			 						@RequestParam(required = false,defaultValue = "1")int size){
		return productServices.findAll(searchKey,current,size);
	}
	
	@GetMapping("/details/{id}")
	public Optional<Product> findByProductId(@PathVariable int id) {
		return productServices.findByProductId(id);
	}
	
	@PatchMapping(path = "updateProduct/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductDto update(@RequestParam("code")String code,@RequestParam("name")String name,
							 @RequestParam("description")String description,@RequestParam("image")Optional<MultipartFile> file,
							 @RequestParam("price")double price,@RequestParam("quantity")int quantity,
							 @PathVariable int id)throws IOException {
		UpdateProductForm form = new UpdateProductForm(code, name, description, file, price, quantity);
		return productServices.update(form,id);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable int id) {
		productServices.delete(id);
	}
	
	@GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        Resource imageResource = new FileSystemResource("D:/Spring/ProductApi/src/main/resources/static/images/" + imageName);
        return ResponseEntity.ok()
                .contentLength(imageResource.contentLength())
                .contentType(MediaType.IMAGE_JPEG) // Set the appropriate MediaType
                .body(imageResource);
    }
	
	@GetMapping("/withProductName/{name}")
	public ProductDto findbyProductName(@PathVariable String name) {
		return productServices.findbyProductName(name);
	}
}






































