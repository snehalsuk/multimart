package com.snehal.multimart.services;

import com.snehal.multimart.payload.CartDto;
import com.snehal.multimart.payload.CategoryDto;

import java.util.List;

public interface CartService {


    CartDto createCart(CartDto cartDto);

    List<CartDto> getAllCarts();

    void deleteCart(Integer cartId);
}
