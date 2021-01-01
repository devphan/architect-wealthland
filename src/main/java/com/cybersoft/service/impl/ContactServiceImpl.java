package com.cybersoft.service.impl;

import com.cybersoft.model.admin.Contact;
import com.cybersoft.repository.ContactRepository;
import com.cybersoft.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ContactServiceImpl implements IService<Contact> {
    @Autowired
    private ContactRepository contactRepository;


    public Page<Contact> findAllPage(Pageable pageable){
        return contactRepository.findAllPage(pageable);
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        return contactRepository.findById(id).get();
    }

    @Override
    public void save(Contact object) {
        contactRepository.save(object);
    }

    @Override
    public void remove(Long id) {
        contactRepository.deleteById(id);
    }
}
