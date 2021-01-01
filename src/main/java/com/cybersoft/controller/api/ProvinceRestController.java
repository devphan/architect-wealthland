package com.cybersoft.controller.api;

import com.cybersoft.model.Province;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ProvinceRestController {
        @Autowired
        private IService<Province> provinceService;

        @GetMapping("/provinces")
        private ResponseEntity<List<Province>> listProvinces(){
            List<Province> provinces= provinceService.findAll ();

            return new ResponseEntity<List<Province>>(provinces, HttpStatus.OK);
        }

        @GetMapping(value = "/provinces/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Province> getProvince(@PathVariable("id") Long id) {
            Province province = provinceService.findById(id);
            if (province == null) {

                return new ResponseEntity<Province>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Province>(province, HttpStatus.OK);
        }

        @PostMapping(value = "/provinces")
        public ResponseEntity<Province> createProvince(@RequestBody Province province, UriComponentsBuilder ucBuilder) {
            try {
                provinceService.save(province);
                return new ResponseEntity<Province>(province, HttpStatus.OK);
            }catch (Exception ex){
                return new ResponseEntity<Province>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PutMapping(value = "/provinces/{id}")
        public ResponseEntity<Province> updateProvince(@PathVariable("id") Long id, @RequestBody Province province) {

            Province currentProvince = provinceService.findById(id);

            if (currentProvince == null) {
                System.out.println("Province with id " + id + " not found");
                return new ResponseEntity<Province>(HttpStatus.NOT_FOUND);
            }

            currentProvince.setName (province.getName ());
            try {
                provinceService.save(currentProvince);
            }catch (Exception ex){
                return new ResponseEntity<Province>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<Province>(currentProvince, HttpStatus.OK);
        }
        @DeleteMapping(value = "/provinces/{id}")
        public ResponseEntity<Province> deleteProvince(@PathVariable("id") Long id){
            Province currentProvince = provinceService.findById(id);
            if (currentProvince == null) {
                System.out.println("Province with id " + id + " not found");
                return new ResponseEntity<Province>(HttpStatus.NOT_FOUND);
            }
            try {
                provinceService.remove (id);
            } catch (Exception e) {
                return new ResponseEntity<Province> (HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<Province>(currentProvince, HttpStatus.OK);
        }
    }


