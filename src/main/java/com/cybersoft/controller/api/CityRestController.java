package com.cybersoft.controller.api;

import com.cybersoft.service.impl.CityServiceImpl;
import com.cybersoft.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CityRestController {
    @Autowired
    private CityServiceImpl cityService;

    @GetMapping("/citys")
    private ResponseEntity<List<City>> listCitys(){
        List<City> citys= cityService.findAll ();

        return new ResponseEntity<List<City>>(citys, HttpStatus.OK);
    }

    @GetMapping(value = "/citys/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<City> getCity(@PathVariable("id") Long id) {
        City city = cityService.findById(id);
        if (city == null) {

            return new ResponseEntity<City>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<City>(city, HttpStatus.OK);
    }
    @GetMapping(value = "/cityAddress/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<City>> getCityAddress(@PathVariable("id") Long id) {
        List<City> city = cityService.findAllByProvince (id);
        if (city == null) {

            return new ResponseEntity<List<City>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<City>>(city, HttpStatus.OK);
    }

    @PostMapping(value = "/citys")
    public ResponseEntity<City> createCity(@RequestBody City city, UriComponentsBuilder ucBuilder) {
        try {
            cityService.save(city);
            return new ResponseEntity<City>(city, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<City>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/citys/{id}")
    public ResponseEntity<City> updateCity(@PathVariable("id") Long id, @RequestBody City city) {

        City currentCity = cityService.findById(id);

        if (currentCity == null) {
            System.out.println("City with id " + id + " not found");
            return new ResponseEntity<City>(HttpStatus.NOT_FOUND);
        }

        currentCity.setName (city.getName ());
        try {
            cityService.save(currentCity);
        }catch (Exception ex){
            return new ResponseEntity<City>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<City>(currentCity, HttpStatus.OK);
    }
    @DeleteMapping(value = "/citys/{id}")
    public ResponseEntity<City> deleteCity(@PathVariable("id") Long id){
        City currentCity = cityService.findById(id);
        if (currentCity == null) {
            System.out.println("City with id " + id + " not found");
            return new ResponseEntity<City>(HttpStatus.NOT_FOUND);
        }
        try {
            cityService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<City> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<City>(currentCity, HttpStatus.OK);
    }
}
