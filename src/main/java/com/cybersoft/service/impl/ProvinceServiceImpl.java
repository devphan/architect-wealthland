package com.cybersoft.service.impl;

import com.cybersoft.repository.ProvinceRepository;
import com.cybersoft.model.Province;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProvinceServiceImpl implements IService<Province> {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public List<Province> findAll() {
        return provinceRepository.findAll ();
    }

    @Override
    public Province findById(Long id) {
        return provinceRepository.findById (id).get ();
    }

    @Override
    public void save(Province object) {
        provinceRepository.save (object);
    }


    @Override
    public void remove(Long id) {
        provinceRepository.deleteById (id);
    }
}
