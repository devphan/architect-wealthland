package com.cybersoft.controller.api;


import com.cybersoft.service.impl.ContactServiceImpl;
import com.cybersoft.model.admin.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactRestController {
    @Autowired
    private ContactServiceImpl contactService;

    @GetMapping("/contacts")
    private ResponseEntity<List<Contact>> listContacts(){
        List<Contact> contacts= contactService.findAll ();

        return new ResponseEntity<List<Contact>>(contacts, HttpStatus.OK);
    }

    @GetMapping(value = "/contacts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> getContact(@PathVariable("id") Long id) {
        Contact contact = contactService.findById(id);
        if (contact == null) {

            return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    }

    @PostMapping(value = "/contacts")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact, UriComponentsBuilder ucBuilder) {
        try {
            contactService.save(contact);
            return new ResponseEntity<Contact>(contact, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Contact>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/contacts/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable("id") Long id, @RequestBody Contact contact) {

        Contact currentContact = contactService.findById(id);

        if (currentContact == null) {
            System.out.println("Contact with id " + id + " not found");
            return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
        }

        currentContact.setEmail (contact.getEmail ());
        currentContact.setName (contact.getName ());

        try {
            contactService.save(currentContact);
        }catch (Exception ex){
            return new ResponseEntity<Contact>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Contact>(currentContact, HttpStatus.OK);
    }
    @DeleteMapping(value = "/contacts/{id}")
    public ResponseEntity<Contact> deleteContact(@PathVariable("id") Long id){
        Contact currentContact = contactService.findById(id);
        if (currentContact == null) {
            System.out.println("Contact with id " + id + " not found");
            return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
        }
        try {
            contactService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<Contact> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Contact>(currentContact, HttpStatus.OK);
    }
}