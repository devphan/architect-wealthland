package com.cybersoft.service.impl;

import com.cybersoft.repository.ClientRepository;
import com.cybersoft.model.admin.Client;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements IService<Client> {
    @Autowired
    private ClientRepository clientRepository;


    public Page<Client> findAllPage(Pageable pageable){
        return clientRepository.findAllPage(pageable);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public void save(Client object) {
        clientRepository.save(object);
    }

    @Override
    public void remove(Long id) {
        clientRepository.deleteById(id);
    }
}
