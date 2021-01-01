package com.cybersoft.controller.api;

import com.cybersoft.service.impl.LayoutServiceImpl;
import com.cybersoft.model.admin.Layout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LayoutRestController {
    @Autowired
    private LayoutServiceImpl layoutService;

    @GetMapping("/introduces")
    private ResponseEntity<List<Layout>> listIntro(){
        List<Layout> layouts= layoutService.introduce ();

        return new ResponseEntity<List<Layout>>(layouts, HttpStatus.OK);
    }

    @GetMapping("/partnerss")
    private ResponseEntity<List<Layout>> listPartner(){
        List<Layout> layouts= layoutService.introducePartner ();

        return new ResponseEntity<List<Layout>>(layouts, HttpStatus.OK);
    }

    @GetMapping("/layouts")
    private ResponseEntity<List<Layout>> listLayouts(){
        List<Layout> layouts= layoutService.findAll ();

        return new ResponseEntity<List<Layout>>(layouts, HttpStatus.OK);
    }

    @GetMapping(value = "/layouts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Layout> getLayout(@PathVariable("id") Long id) {
        Layout layout = layoutService.findById(id);
        if (layout == null) {

            return new ResponseEntity<Layout>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Layout>(layout, HttpStatus.OK);
    }

    @PostMapping(value = "/layouts")
    public ResponseEntity<Layout> createLayout(@RequestBody Layout layout, UriComponentsBuilder ucBuilder) {
        try {
            layoutService.save(layout);
            return new ResponseEntity<Layout>(layout, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Layout>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/layouts/{id}")
    public ResponseEntity<Layout> updateLayout(@PathVariable("id") Long id, @RequestBody Layout layout) {

        Layout currentLayout = layoutService.findById(id);

        if (currentLayout == null) {
            System.out.println("Layout with id " + id + " not found");
            return new ResponseEntity<Layout>(HttpStatus.NOT_FOUND);
        }

        currentLayout.setDescription (layout.getDescription ());
        currentLayout.setName (layout.getName ());
  
        try {
            layoutService.save(currentLayout);
        }catch (Exception ex){
            return new ResponseEntity<Layout>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Layout>(currentLayout, HttpStatus.OK);
    }
    @DeleteMapping(value = "/layouts/{id}")
    public ResponseEntity<Layout> deleteLayout(@PathVariable("id") Long id){
        Layout currentLayout = layoutService.findById(id);
        if (currentLayout == null) {
            System.out.println("Layout with id " + id + " not found");
            return new ResponseEntity<Layout>(HttpStatus.NOT_FOUND);
        }
        try {
            layoutService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<Layout> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Layout>(currentLayout, HttpStatus.OK);
    }
}
