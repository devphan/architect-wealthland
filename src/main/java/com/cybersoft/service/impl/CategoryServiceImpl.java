package com.cybersoft.service.impl;

import com.cybersoft.repository.CategoryRepository;
import com.cybersoft.model.admin.Category;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements IService<Category> {
    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> findAllPage(Pageable pageable){
        return categoryRepository.findAllPage(pageable);
    }


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public void save(Category object) {
        categoryRepository.save(object);
    }

    @Override
    public void remove(Long id) {
        categoryRepository.deleteById(id);
    }
}
