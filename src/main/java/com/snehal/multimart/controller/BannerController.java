package com.snehal.multimart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snehal.multimart.payload.BannerDto;
import com.snehal.multimart.services.BannerService;
import com.snehal.multimart.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;




@CrossOrigin(origins = {"http://localhost:3000"},
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.OPTIONS})
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;




    @PostMapping("/posts")
    public ResponseEntity<BannerDto> createProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("userData") String userData

    ) throws IOException {

        BannerDto bannerDto = null;
        bannerDto = mapper.readValue(userData, BannerDto.class);
        String fileName = this.fileService.uploadImage(path, image);
        bannerDto.setImage(fileName);
        BannerDto createBan=this.bannerService.createBanner(bannerDto);
        return new ResponseEntity<BannerDto>(createBan, HttpStatus.CREATED);
    }


    @GetMapping("/getAll")
    public List<BannerDto> getAllProduct() {

        List<BannerDto> allBanner = this.bannerService.getAllBanner();

        return allBanner;
    }

    @GetMapping(value = "image/{image}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("image") String image,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, image);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }


}
