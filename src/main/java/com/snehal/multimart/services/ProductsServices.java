package com.snehal.multimart.services;



import com.snehal.multimart.payload.ProductDto;

import java.util.List;

public interface ProductsServices {

    ProductDto createProduct(ProductDto productDto, Integer userId, Integer categoryId);
    ProductDto updateProduct(ProductDto productDto, Integer categoryId);
   ProductDto getProductById(Integer prodId);
    List<ProductDto> getAllProduct();
    void deleteProduct (Integer prodId);

    List<ProductDto> getProductByCategory(Integer categoryId);
    List<ProductDto> getProductByUserInfo(Integer userId);
    List<ProductDto> searchProduct(String keyword);


}
