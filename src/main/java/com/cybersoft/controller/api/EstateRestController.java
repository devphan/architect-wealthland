package com.cybersoft.controller.api;


import com.cybersoft.service.impl.EstateServiceImpl;
import com.cybersoft.model.admin.Estate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EstateRestController {
    @Autowired
    private EstateServiceImpl estateService;

    @PostMapping("/filterEstate")
    private ResponseEntity<List<Estate>> filter(@RequestBody Estate estate){
        List<Estate> estates  = estateService.findAll ();
        if (estate.getPrice () == 1) {
            estates = estateService.filterbyPrice (0L, 1000000000L);
        } else if (estate.getPrice () == 2) {
            estates = estateService.filterbyPrice (1000000000L, 3000000000L);
        } else if (estate.getPrice () == 3) {
            estates = estateService.filterbyPrice (3000000000L, 5000000000L);

        } else if (estate.getPrice () == 4) {
            estates = estateService.filterbyPrice (5000000000L, 100000000000L);
        }
//        if (estate.getProject () != null) {
//            estates = estateService.findByProject (estate.getProject ().getId ());
//        }
//        if(estate.getDirection () != null) {
//            estates = estateService.findByDirection (estate.getDirection ());
//        }
//        if(estate.getPriceValue () != null) {
//            estates = estateService.findByPrice (estate.getPriceValue ());
//        }
//        if(estate.getAreaValue () != null) {
//            estates = estateService.findByArea (estate.getAreaValue ());
//        }
//        if(estate.getCity () != null) {
//            estates = estateService.findByCity (estate.getCity ().getId ());
//        }
        return new ResponseEntity<List<Estate>>(estates, HttpStatus.OK);
    }

    @GetMapping("/hotEstate")
    private ResponseEntity<List<Estate>> listHotEstates(){
        List<Estate> estates= estateService.findByHot ();

        return new ResponseEntity<List<Estate>>(estates, HttpStatus.OK);
    }

    @GetMapping("/estateCategory/{id}")
    private ResponseEntity<List<Estate>> listEstateCategory(@PathVariable("id") Long category){
        List<Estate> estates= estateService.findByCategory (category);
        return new ResponseEntity<List<Estate>>(estates, HttpStatus.OK);
    }

    @GetMapping("/estates")
    private ResponseEntity<List<Estate>> listEstates(){
        List<Estate> estates= estateService.findAll ();

        return new ResponseEntity<List<Estate>>(estates, HttpStatus.OK);
    }

    @GetMapping(value = "/estates/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Estate> getEstate(@PathVariable("id") Long id) {
        Estate estate = estateService.findById(id);
        if (estate == null) {

            return new ResponseEntity<Estate>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Estate>(estate, HttpStatus.OK);
    }

    @PostMapping(value = "/estates")
    public ResponseEntity<Estate> createEstate(@RequestBody Estate estate, UriComponentsBuilder ucBuilder) {
        try {
            estateService.save(estate);
            return new ResponseEntity<Estate>(estate, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Estate>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/estates/{id}")
    public ResponseEntity<Estate> updateEstate(@PathVariable("id") Long id, @RequestBody Estate estate) {

        Estate currentEstate = estateService.findById(id);

        if (currentEstate == null) {
            System.out.println("Estate with id " + id + " not found");
            return new ResponseEntity<Estate>(HttpStatus.NOT_FOUND);
        }

        currentEstate.setDescription (estate.getDescription ());
        currentEstate.setImage (estate.getImage ());
        currentEstate.setName (estate.getName ());
        currentEstate.setPrice (estate.getPrice ());
        currentEstate.setArea (estate.getArea ());
        currentEstate.setDirection (estate.getDirection ());
        currentEstate.setBank (estate.getBank ());
        currentEstate.setBathroom (estate.getBathroom ());
        currentEstate.setStatus (estate.getStatus ());

        currentEstate.setNews (estate.getNews ());
        currentEstate.setProvince (estate.getProvince ());
        currentEstate.setDirection (estate.getDirection ());
        currentEstate.setHot(estate.isHot ());
        currentEstate.setCity (estate.getCity ());
        try {
            estateService.save(currentEstate);
        }catch (Exception ex){
            return new ResponseEntity<Estate>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Estate>(currentEstate, HttpStatus.OK);
    }
    @DeleteMapping(value = "/estates/{id}")
    public ResponseEntity<Estate> deleteEstate(@PathVariable("id") Long id){
        Estate currentEstate = estateService.findById(id);
        if (currentEstate == null) {
            System.out.println("Estate with id " + id + " not found");
            return new ResponseEntity<Estate>(HttpStatus.NOT_FOUND);
        }
        try {
            estateService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<Estate> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Estate>(currentEstate, HttpStatus.OK);
    }
}
