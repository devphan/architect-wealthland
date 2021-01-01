package com.cybersoft.controller.api;

import com.cybersoft.service.impl.BankServiceImpl;
import com.cybersoft.model.admin.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankRestController {
    @Autowired
    private BankServiceImpl bankService;

    @GetMapping("/banks")
    private ResponseEntity<List<Bank>> listBanks(){
        List<Bank> banks= bankService.findAll ();

        return new ResponseEntity<List<Bank>>(banks, HttpStatus.OK);
    }

    @GetMapping(value = "/banks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bank> getBank(@PathVariable("id") Long id) {
        Bank bank = bankService.findById(id);
        if (bank == null) {

            return new ResponseEntity<Bank>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Bank>(bank, HttpStatus.OK);
    }

    @PostMapping(value = "/banks")
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank, UriComponentsBuilder ucBuilder) {
        try {
            bankService.save(bank);
            return new ResponseEntity<Bank>(bank, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Bank>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/banks/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable("id") Long id, @RequestBody Bank bank) {

        Bank currentBank = bankService.findById(id);

        if (currentBank == null) {
            System.out.println("Bank with id " + id + " not found");
            return new ResponseEntity<Bank>(HttpStatus.NOT_FOUND);
        }

        currentBank.setDescription (bank.getDescription ());
        currentBank.setImage (bank.getImage ());
        currentBank.setAddress (bank.getAddress ());
        currentBank.setName (bank.getName ());
        try {
            bankService.save(currentBank);
        }catch (Exception ex){
            return new ResponseEntity<Bank>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Bank>(currentBank, HttpStatus.OK);
    }
    @DeleteMapping(value = "/banks/{id}")
    public ResponseEntity<Bank> deleteBank(@PathVariable("id") Long id){
        Bank currentBank = bankService.findById(id);
        if (currentBank == null) {
            System.out.println("Bank with id " + id + " not found");
            return new ResponseEntity<Bank>(HttpStatus.NOT_FOUND);
        }
        try {
            bankService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<Bank> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Bank>(currentBank, HttpStatus.OK);
    }
}
