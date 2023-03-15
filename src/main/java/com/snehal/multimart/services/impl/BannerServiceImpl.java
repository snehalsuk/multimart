package com.snehal.multimart.services.impl;

import com.snehal.multimart.entities.Banner;
import com.snehal.multimart.entities.ProductEntity;
import com.snehal.multimart.payload.BannerDto;
import com.snehal.multimart.payload.ProductDto;
import com.snehal.multimart.repo.BannerRepo;
import com.snehal.multimart.services.BannerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BannerServiceImpl implements BannerService {


    @Autowired
    private BannerRepo bannerRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BannerDto createBanner(BannerDto bannerDto) {

        Banner banner = this.modelMapper.map(bannerDto, Banner.class);
        banner.setTitle(bannerDto.getTitle());

        banner.setImage(bannerDto.getImage());


        Banner newBanner=this.bannerRepo.save(banner);


        return this.modelMapper.map(newBanner, BannerDto.class);

    }

    @Override
    public List<BannerDto> getAllBanner() {
        List<Banner> allBan = this.bannerRepo.findAll();
        List<BannerDto> bannerDtos = allBan.stream().map((cs) -> this.modelMapper.map(cs, BannerDto.class)).collect(Collectors.toList());

        return bannerDtos;
    }
}
