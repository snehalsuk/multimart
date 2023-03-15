package com.snehal.multimart.services.impl;

import com.snehal.multimart.entities.Cart;
import com.snehal.multimart.entities.Category;
import com.snehal.multimart.exception.ResourceNotFoundException;
import com.snehal.multimart.payload.CartDto;
import com.snehal.multimart.payload.CategoryDto;
import com.snehal.multimart.repo.CartRepo;
import com.snehal.multimart.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepo cartRepo;



    @Override
    public CartDto createCart(CartDto cartDto) {
        Cart cat=this.modelMapper.map(cartDto, Cart.class);
        Cart addedCart=this.cartRepo.save(cat);

        return this.modelMapper.map(addedCart, CartDto.class);
    }

    @Override
    public List<CartDto> getAllCarts() {

        List<Cart> carts= this.cartRepo.findAll();
        List<CartDto> cartDtos=   carts.stream().map((cat) ->
                this.modelMapper.map(cat, CartDto.class)).collect(Collectors.toList());


        return cartDtos;
    }

    @Override
    public void deleteCart(Integer cartId) {
        Cart cat= this.cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cart id", cartId));
        this.cartRepo.delete(cat);
    }
}
