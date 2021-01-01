package com.cybersoft.service.impl;

import com.cybersoft.repository.BannerRepository;
import com.cybersoft.model.admin.Banner;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BannerServiceImpl implements IService<Banner> {
    @Autowired
    private BannerRepository bannerRepository;

    public Page<Banner> findAllPage(Pageable pageable){
        return bannerRepository.findAllPage(pageable);
    }

    @Override
    public List<Banner> findAll() {
        return bannerRepository.findAll();
    }

    public Banner findBanner() {
        return bannerRepository.findBanner();
    }

    @Override
    public Banner findById(Long id) {
        return bannerRepository.findById(id).get();
    }

    @Override
    public void save(Banner object) {
        bannerRepository.save(object);
    }

    @Override
    public void remove(Long id) {
        bannerRepository.deleteById(id);
    }
}
