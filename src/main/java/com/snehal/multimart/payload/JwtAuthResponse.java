package com.snehal.multimart.payload;


import com.snehal.multimart.entities.UserInfo;
import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;

    private UserInfo userInfo;
}
