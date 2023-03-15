package com.snehal.multimart.services.impl;



import com.snehal.multimart.entities.Category;
import com.snehal.multimart.entities.ProductEntity;
import com.snehal.multimart.entities.UserInfo;
import com.snehal.multimart.exception.ResourceNotFoundException;
import com.snehal.multimart.payload.ProductDto;
import com.snehal.multimart.repo.CategoryRepo;
import com.snehal.multimart.repo.ProductRepo;
import com.snehal.multimart.repository.UserInfoRepository;
import com.snehal.multimart.services.ProductsServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServicesImpl implements ProductsServices {

@Autowired
    private ProductRepo productRepo;

@Autowired
private UserInfoRepository userRepo;

@Autowired
private CategoryRepo categoryRepo;

@Autowired
private ModelMapper modelMapper;






    @Override
    public ProductDto createProduct(ProductDto productDto, Integer userId, Integer categoryId) {

        UserInfo userInfo= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User id", userId));
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category id", categoryId));

        ProductEntity product = this.modelMapper.map(productDto, ProductEntity.class);
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        product.setUserInfo(userInfo);
        product.setCategory(category);


        ProductEntity newProduct=this.productRepo.save(product);


        return this.modelMapper.map(newProduct, ProductDto.class);

    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer prodId) {

        ProductEntity blog = this.productRepo.findById(prodId)
                .orElseThrow(() -> new ResourceNotFoundException("BlogEntity", "blogId", prodId));

        blog.setTitle(productDto.getTitle());
        blog.setDescription(productDto.getDescription());
        blog.setImage(productDto.getImage());

       ProductEntity updateBlog=this.productRepo.save(blog);
        return this.modelMapper.map(updateBlog, ProductDto.class);


    }

    @Override
    public ProductDto getProductById(Integer blogId) {

        ProductEntity blog = this.productRepo.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("BlogEntity", "blogId", blogId));


        return this.modelMapper.map(blog, ProductDto.class);
    }



    @Override
    public List<ProductDto> getAllProduct() {

        List<ProductEntity> allBlog = this.productRepo.findAll();
        List<ProductDto> productDtos = allBlog.stream().map((cs) -> this.modelMapper.map(cs, ProductDto.class)).collect(Collectors.toList());

        return productDtos;
    }

    @Override
    public void deleteProduct(Integer prodId) {

        ProductEntity blogs = this.productRepo.findById(prodId)
                .orElseThrow(() -> new ResourceNotFoundException("BlogEntity", "blogId", prodId));

        this.productRepo.delete(blogs);
    }

    @Override
    public List<ProductDto> getProductByCategory(Integer categoryId) {

        Category cat= this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category id", categoryId));
        List<ProductEntity> posts= this.productRepo.findByCategory(cat);

        List<ProductDto> postDtos= posts.stream().map((post)-> this.modelMapper.map(post,ProductDto.class)).collect(Collectors.toList());


        return postDtos;
    }

    @Override
    public List<ProductDto> getProductByUserInfo(Integer userId) {

        UserInfo userInfo=this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("UserInfo","userId",userId));
        List<ProductEntity> products=this.productRepo.findByUserInfo(userInfo);

        List<ProductDto> productDtos= products.stream().map((prod)-> this.modelMapper.map(prod, ProductDto.class)).collect(Collectors.toList());

        return productDtos;
    }

    @Override
    public List<ProductDto> searchProduct(String keyword) {

        List<ProductEntity> products=this.productRepo.findByTitleContaining(keyword);

        List<ProductDto> productDtos = products.stream().map((prod) -> this.modelMapper.map(prod, ProductDto.class))
                .collect(Collectors.toList());

        return productDtos;
    }


}
