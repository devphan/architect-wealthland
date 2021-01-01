package com.cybersoft.service.impl;

import com.cybersoft.repository.LayoutRepository;
import com.cybersoft.model.admin.Layout;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LayoutServiceImpl implements IService<Layout> {
    @Autowired
    private LayoutRepository layoutRepository;

    public Page<Layout> findAllPage(Pageable pageable){
        return layoutRepository.findAllPage(pageable);
    }


    @Override
    public List<Layout> findAll() {
        return layoutRepository.findAll();
    }

    @Override
    public Layout findById(Long id) {
        return layoutRepository.findById(id).get();
    }

    @Override
    public void save(Layout object) {
        layoutRepository.save(object);
    }

    @Override
    public void remove(Long id) {
        layoutRepository.deleteById(id);
    }

    public Layout findByAboutTab(String tab) {
        return layoutRepository.findByAboutTab(tab);
    }

    public Layout findByPartnerTab(String tab) {
        return layoutRepository.findByPartnerTab (tab);
    }

    public List<Layout> introduce() {
        return layoutRepository.introduce ();
    }

    public List<Layout> introducePartner() {
        return layoutRepository.introducePartner ();
    }

}
