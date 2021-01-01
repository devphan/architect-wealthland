package com.cybersoft.controller.api;


import com.cybersoft.service.impl.CategoryServiceImpl;
import com.cybersoft.model.admin.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryRestController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/categorys")
    private ResponseEntity<List<Category>> listCategorys(){
        List<Category> categorys= categoryService.findAll ();

        return new ResponseEntity<List<Category>>(categorys, HttpStatus.OK);
    }

    @GetMapping(value = "/categorys/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategory(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {

            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }

    @PostMapping(value = "/categorys")
    public ResponseEntity<Category> createCategory(@RequestBody Category category, UriComponentsBuilder ucBuilder) {
        try {
            categoryService.save(category);
            return new ResponseEntity<Category>(category, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Category>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/categorys/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody Category category) {

        Category currentCategory = categoryService.findById(id);

        if (currentCategory == null) {
            System.out.println("Category with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }

        currentCategory.setName (category.getName ());
        try {
            categoryService.save(currentCategory);
        }catch (Exception ex){
            return new ResponseEntity<Category>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Category>(currentCategory, HttpStatus.OK);
    }
    @DeleteMapping(value = "/categorys/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") Long id){
        Category currentCategory = categoryService.findById(id);
        if (currentCategory == null) {
            System.out.println("Category with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        try {
            categoryService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<Category> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Category>(currentCategory, HttpStatus.OK);
    }
}