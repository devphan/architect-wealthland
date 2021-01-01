package com.cybersoft.service.impl;

import com.cybersoft.repository.EstateRepository;
import com.cybersoft.model.admin.Estate;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EstateServiceImpl implements IService<Estate> {
    @Autowired
    private EstateRepository estateRepository;

    public Page<Estate> findAllPage(Pageable pageable){
        return estateRepository.findAllPage(pageable);
    }


    @Override
    public List<Estate> findAll() {
        return estateRepository.findAll();
    }

    @Override
    public Estate findById(Long id) {
        return estateRepository.findById(id).get();
    }

    @Override
    public void save(Estate object) {
        estateRepository.save(object);
    }

    @Override
    public void remove(Long id) {
        estateRepository.deleteById(id);
    }

    public List<Estate> findByProject(Long id) { return estateRepository.findByProject (id);}

    public List<Estate> findByDirection(String direction) { return estateRepository.findByDirection (direction);}

    public List<Estate> findByPrice(Long price) { return estateRepository.findByPrice (price);}

    public List<Estate> findByArea(Long area) { return estateRepository.findByArea (area);}

    public List<Estate> findByCity(Long city) { return estateRepository.findByCity (city);}

    public List<Estate> findByHot() { return estateRepository.findHotest ();}

    public List<Estate> findByCategory(Long category) { return estateRepository.findByCategory (category);}

    public List<Estate> filterbyPrice(Long min, Long max) {return estateRepository.filterPrice (min,max);}

}
