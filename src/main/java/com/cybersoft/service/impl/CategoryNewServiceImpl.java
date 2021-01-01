package com.cybersoft.service.impl;

import com.cybersoft.repository.CategoryNewsRepository;
import com.cybersoft.model.admin.Category_News;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryNewServiceImpl implements IService<Category_News> {
    @Autowired
    private CategoryNewsRepository categoryNewsRepository;

    @Override
    public List<Category_News> findAll() {
        return categoryNewsRepository.findAll();
    }

    public Page<Category_News> findAllPage(Pageable pageable){
        return categoryNewsRepository.findAllPage(pageable);
    }

    @Override
    public Category_News findById(Long id) {
        return categoryNewsRepository.findById(id).get();
    }

    @Override
    public void save(Category_News object) {
        categoryNewsRepository.save(object);
    }

    @Override
    public void remove(Long id) {
        categoryNewsRepository.deleteById(id);
    }
}
