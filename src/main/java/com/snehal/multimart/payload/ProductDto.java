package com.snehal.multimart.payload;



import com.snehal.multimart.dto.AuthRequest;
import com.snehal.multimart.entities.Category;
import com.snehal.multimart.entities.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {


    private int Id;
    private String title;

    private String description;

    private int price;
    private String image;

    private CategoryDto category;

    private AuthRequest authRequest;


}
