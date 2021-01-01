package com.cybersoft.service.impl;

import com.cybersoft.repository.BankRepository;
import com.cybersoft.model.admin.Bank;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BankServiceImpl implements IService<Bank> {
    @Autowired
    private BankRepository bankRepository;

    public Page<Bank> findAllPage(Pageable pageable){
        return bankRepository.findAllPage(pageable);
    }

    @Override
    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    @Override
    public Bank findById(Long id) {
        return bankRepository.findById(id).get();
    }

    @Override
    public void save(Bank object) {
        bankRepository.save(object);
    }

    @Override
    public void remove(Long id) {
        bankRepository.deleteById(id);
    }
}
