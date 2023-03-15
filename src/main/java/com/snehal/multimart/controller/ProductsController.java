package com.snehal.multimart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.snehal.multimart.payload.ProductDto;
import com.snehal.multimart.services.FileService;
import com.snehal.multimart.services.ProductsServices;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@CrossOrigin(origins = {"http://localhost:3000"},
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.OPTIONS})
@RestController
@RequestMapping("/prod")
public class ProductsController {

    @Autowired
    private FileService fileService;


    @Autowired
    private ObjectMapper mapper;

    @Autowired
   private ProductsServices productsServices;


    @Value("${project.image}")
    private String path;




    @GetMapping("/get")
    public List<ProductDto> getAllProduct() {

        List<ProductDto> allProducts = this.productsServices.getAllProduct();

        return allProducts;
    }


    @GetMapping("/get-id/{prodId}")
    public ProductDto getByIdProduct(
            @PathVariable Integer prodId) {
        ProductDto prodById = this.productsServices.getProductById(prodId);
        return prodById;
    }




    //method to serve image
    @GetMapping(value = "image/{image}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("image") String image,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, image);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }




    @DeleteMapping("/image/{prodId}")
    public ResponseEntity<?> uploadPostImage(
             @PathVariable Integer prodId
    ) throws IOException {
        this.productsServices.deleteProduct(prodId);


        return new ResponseEntity<>(HttpStatus.OK);
    }




        @PostMapping("/user/{userId}/category/{categoryId}/posts")
        public ResponseEntity<ProductDto> createProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("userData") String userData,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
    ) throws IOException {

        ProductDto productDto = null;
        productDto = mapper.readValue(userData, ProductDto.class);
        String fileName = this.fileService.uploadImage(path, image);
        productDto.setImage(fileName);
        ProductDto createBlog=this.productsServices.createProduct(productDto,userId,categoryId);
        return new ResponseEntity<ProductDto>(createBlog,HttpStatus.CREATED);
        }




    @PutMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("userData") String userData,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId
    ) throws IOException {

        ProductDto productDto = null;
        productDto = mapper.readValue(userData, ProductDto.class);
        String fileName = this.fileService.uploadImage(path, image);
        productDto.setImage(fileName);
        ProductDto createBlog=this.productsServices.updateProduct(productDto,categoryId);
        return new ResponseEntity<ProductDto>(createBlog,HttpStatus.CREATED);
    }



   @GetMapping("/products/search/{keywords}")
    public ResponseEntity<List<ProductDto>> searchProductByTitle(
            @PathVariable("keywords") String keywords
    ){
        List<ProductDto> result= this.productsServices.searchProduct(keywords);
        return new ResponseEntity<List<ProductDto>>(result,HttpStatus.OK);
    }



    @GetMapping("/category/{categoryId}/products")
    public ResponseEntity<List<ProductDto>> getProductByCategory(@PathVariable Integer categoryId){
        List<ProductDto> products=this.productsServices.getProductByCategory(categoryId);
        return new ResponseEntity<List<ProductDto>>(products,HttpStatus.OK);

    }


    @GetMapping("/user/{userId}/products")
    public ResponseEntity<List<ProductDto>> getProductByUser(@PathVariable Integer userId){

        List<ProductDto> posts= this.productsServices.getProductByUserInfo(userId);

        return new ResponseEntity<List<ProductDto>>(posts,HttpStatus.OK);

    }



}