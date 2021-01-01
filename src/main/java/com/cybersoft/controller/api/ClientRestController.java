package com.cybersoft.controller.api;

import com.cybersoft.service.impl.ClientServiceImpl;
import com.cybersoft.model.admin.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientRestController {
    @Autowired
    private ClientServiceImpl clientService;

    @GetMapping("/clients")
    private ResponseEntity<List<Client>> listClients(){
        List<Client> clients= clientService.findAll ();

        return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
    }

    @GetMapping(value = "/clients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> getClient(@PathVariable("id") Long id) {
        Client client = clientService.findById(id);
        if (client == null) {

            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @PostMapping(value = "/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client, UriComponentsBuilder ucBuilder) {
        try {
            String status = "Not Done";
            client.setStatus(status);
            clientService.save(client);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") Long id, @RequestBody Client client) {

        Client currentClient = clientService.findById(id);

        if (currentClient == null) {
            System.out.println("Client with id " + id + " not found");
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }


        currentClient.setEmail (client.getEmail ());
        currentClient.setName (client.getName ());
        currentClient.setStatus (client.getStatus ());
        currentClient.setTel (client.getTel ());
        try {
            clientService.save(currentClient);
        }catch (Exception ex){
            return new ResponseEntity<Client>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Client>(currentClient, HttpStatus.OK);
    }
    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable("id") Long id){
        Client currentClient = clientService.findById(id);
        if (currentClient == null) {
            System.out.println("Client with id " + id + " not found");
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
        try {
            clientService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<Client> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Client>(currentClient, HttpStatus.OK);
    }
}