package com.cybersoft.controller.api;

import com.cybersoft.service.impl.CategoryNewServiceImpl;
import com.cybersoft.model.admin.Category_News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryNewsRestController {
    @Autowired
    private CategoryNewServiceImpl categoryNewService;

    @GetMapping("/categoryNews")
    private ResponseEntity<List<Category_News>> listCategories(){
        List<Category_News> categories= categoryNewService.findAll ();

        return new ResponseEntity<List<Category_News>>(categories, HttpStatus.OK);
    }

    @GetMapping(value = "/categoryNews/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category_News> getCategory(@PathVariable("id") Long id) {
        Category_News category = categoryNewService.findById(id);
        if (category == null) {

            return new ResponseEntity<Category_News>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Category_News>(category, HttpStatus.OK);
    }

    @PostMapping(value = "/categoryNews")
    public ResponseEntity<Category_News> createCategory(@RequestBody Category_News category, UriComponentsBuilder ucBuilder) {
        try {
            categoryNewService.save(category);
            return new ResponseEntity<Category_News>(category, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Category_News>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/categoryNews/{id}")
    public ResponseEntity<Category_News> updateCategory(@PathVariable("id") Long id, @RequestBody Category_News category) {

        Category_News currentCategory = categoryNewService.findById(id);

        if (currentCategory == null) {
            System.out.println("Category with id " + id + " not found");
            return new ResponseEntity<Category_News>(HttpStatus.NOT_FOUND);
        }

        currentCategory.setName (category.getName ());
        try {
            categoryNewService.save(currentCategory);
        }catch (Exception ex){
            return new ResponseEntity<Category_News>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Category_News>(currentCategory, HttpStatus.OK);
    }
    @DeleteMapping(value = "/categoryNews/{id}")
    public ResponseEntity<Category_News> deleteCategory(@PathVariable("id") Long id){
        Category_News currentCategory = categoryNewService.findById(id);
        if (currentCategory == null) {
            System.out.println("Category with id " + id + " not found");
            return new ResponseEntity<Category_News>(HttpStatus.NOT_FOUND);
        }
        try {
            categoryNewService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<Category_News> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Category_News>(currentCategory, HttpStatus.OK);
    }
}
