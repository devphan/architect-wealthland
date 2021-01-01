package com.cybersoft.service.impl;

import com.cybersoft.repository.NewsRepository;
import com.cybersoft.model.admin.News;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class NewsServiceImpl implements IService<News> {
    @Autowired
    private NewsRepository newsRepository;

    public Page<News> findAllPage(Pageable pageable){
        return newsRepository.findAllPage(pageable);
    }


    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id).get();
    }

    @Override
    public void save(News object) {
        newsRepository.save(object);
    }

    @Override
    public void remove(Long id) {
        newsRepository.deleteById(id);
    }

    public List<News> findByCategoryNews(Long category_news) { return newsRepository.findByCategoryNews (category_news); }
    public List<News> findByEstate(Long estate) { return newsRepository.findByEstate (estate); }
//    public List<News> findByCategory_News(Category_News id){return newsRepository.findByCategory_news(id);}
}
