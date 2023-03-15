package com.snehal.multimart.repo;


import com.snehal.multimart.entities.Category;
import com.snehal.multimart.entities.ProductEntity;
import com.snehal.multimart.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface ProductRepo extends JpaRepository<ProductEntity,Integer> {

List<ProductEntity> findByUserInfo(UserInfo userInfo);
List<ProductEntity> findByCategory(Category category);

List<ProductEntity> findByTitleContaining(String title);


}
