package com.snehal.multimart.services;

import com.snehal.multimart.payload.BannerDto;

import java.util.List;

public interface BannerService {

    BannerDto createBanner(BannerDto bannerDto);

    List<BannerDto> getAllBanner();
}
