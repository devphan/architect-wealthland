package com.cybersoft.controller.api;

import com.cybersoft.service.impl.BannerServiceImpl;
import com.cybersoft.model.admin.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BannerRestController {
    @Autowired
    private BannerServiceImpl bannerService;

    @GetMapping("/banners")
    private ResponseEntity<List<Banner>> listBanners(){
        List<Banner> banners= bannerService.findAll ();

        return new ResponseEntity<List<Banner>>(banners, HttpStatus.OK);
    }

    @GetMapping(value = "/banners/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Banner> getBanner(@PathVariable("id") Long id) {
        Banner banner = bannerService.findById(id);
        if (banner == null) {

            return new ResponseEntity<Banner>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Banner>(banner, HttpStatus.OK);
    }

    @PostMapping(value = "/banners")
    public ResponseEntity<Banner> createBanner(@RequestBody Banner banner, UriComponentsBuilder ucBuilder) {
        try {
            bannerService.save(banner);
            return new ResponseEntity<Banner>(banner, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Banner>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/banners/{id}")
    public ResponseEntity<Banner> updateBanner(@PathVariable("id") Long id, @RequestBody Banner banner) {

        Banner currentBanner = bannerService.findById(id);

        if (currentBanner == null) {
            System.out.println("Banner with id " + id + " not found");
            return new ResponseEntity<Banner>(HttpStatus.NOT_FOUND);
        }

        currentBanner.setDescription (banner.getDescription ());
        currentBanner.setImage (banner.getImage ());
        currentBanner.setTitle1 (banner.getTitle1 ());
        currentBanner.setTitle2 (banner.getTitle2 ());
        try {
            bannerService.save(currentBanner);
        }catch (Exception ex){
            return new ResponseEntity<Banner>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Banner>(currentBanner, HttpStatus.OK);
    }
    @DeleteMapping(value = "/banners/{id}")
    public ResponseEntity<Banner> deleteBanner(@PathVariable("id") Long id){
        Banner currentBanner = bannerService.findById(id);
        if (currentBanner == null) {
            System.out.println("Banner with id " + id + " not found");
            return new ResponseEntity<Banner>(HttpStatus.NOT_FOUND);
        }
        try {
            bannerService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<Banner> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Banner>(currentBanner, HttpStatus.OK);
    }
}