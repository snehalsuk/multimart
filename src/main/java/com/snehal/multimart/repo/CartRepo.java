package com.snehal.multimart.repo;

import com.snehal.multimart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart,Integer> {

}
