package com.cybersoft.service.impl;

import com.cybersoft.repository.CityRepository;
import com.cybersoft.model.City;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class CityServiceImpl implements IService<City> {
    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll ();
    }

    @Override
    public City findById(Long id) {
        return cityRepository.findById (id).get ();
    }

    @Override
    public void save(City object) {
        cityRepository.save (object);
    }

    @Override
    public void remove(Long id) {
        cityRepository.deleteById (id);
    }

    public List<City> findAllByProvince(Long id){
        return cityRepository.findByAddress (id);
    }
}
