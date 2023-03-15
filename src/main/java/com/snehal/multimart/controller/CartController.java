package com.snehal.multimart.controller;


import com.snehal.multimart.payload.CartDto;
import com.snehal.multimart.payload.CategoryDto;
import com.snehal.multimart.services.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"},
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.OPTIONS})
@RestController
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private CartService cartService;



    @PostMapping("/items")
    public ResponseEntity<CartDto> createCart( @RequestBody CartDto cartDto){
        CartDto createCart = this.cartService.createCart(cartDto);
        return new ResponseEntity<CartDto>(createCart, HttpStatus.CREATED);

    }


    @GetMapping("/getcart")
    public ResponseEntity<List<CartDto>> getCarts(){
        List<CartDto> carts=this.cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Integer cartId){
        this.cartService.deleteCart( cartId);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
