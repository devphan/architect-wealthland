package com.cybersoft.controller.api;


import com.cybersoft.service.impl.NewsServiceImpl;
import com.cybersoft.model.admin.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NewsRestController {
    @Autowired
    private NewsServiceImpl newsService;

    @GetMapping("/categoryNewss/{id}")
    private ResponseEntity<List<News>> listCategoryNewss(@PathVariable("id") Long category_news){
        List<News> newss= newsService.findByCategoryNews (category_news);
        return new ResponseEntity<List<News>>(newss, HttpStatus.OK);
    }

    @GetMapping("/newss")
    private ResponseEntity<List<News>> listNewss(){
        List<News> newss= newsService.findAll ();

        return new ResponseEntity<List<News>>(newss, HttpStatus.OK);
    }

    @GetMapping(value = "/newss/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<News> getNews(@PathVariable("id") Long id) {
        News news = newsService.findById(id);
        if (news == null) {

            return new ResponseEntity<News>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<News>(news, HttpStatus.OK);
    }

    @PostMapping(value = "/newss")
    public ResponseEntity<News> createNews(@RequestBody News news, UriComponentsBuilder ucBuilder) {
        try {
            newsService.save(news);
            return new ResponseEntity<News>(news, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<News>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/newss/{id}")
    public ResponseEntity<News> updateNews(@PathVariable("id") Long id, @RequestBody News news) {

        News currentNews = newsService.findById(id);

        if (currentNews == null) {
            System.out.println("News with id " + id + " not found");
            return new ResponseEntity<News>(HttpStatus.NOT_FOUND);
        }

        currentNews.setDescription (news.getDescription());
        currentNews.setImage (news.getImage ());
        currentNews.setCategory_news (news.getCategory_news ());
        currentNews.setTitle1 (news.getTitle1 ());
        try {
            newsService.save(currentNews);
        }catch (Exception ex){
            return new ResponseEntity<News>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<News>(currentNews, HttpStatus.OK);
    }
    @DeleteMapping(value = "/newss/{id}")
    public ResponseEntity<News> deleteNews(@PathVariable("id") Long id){
        News currentNews = newsService.findById(id);
        if (currentNews == null) {
            System.out.println("News with id " + id + " not found");
            return new ResponseEntity<News>(HttpStatus.NOT_FOUND);
        }
        try {
            newsService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<News> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<News>(currentNews, HttpStatus.OK);
    }
}