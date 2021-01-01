package com.cybersoft.model.admin;


import org.hibernate.annotations.Where;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Where(clause = "deleted = 0")
public class Client implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String tel;
    private String email;
    private String description;
    private String status;
    private short deleted;

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;
        String clientName = client.getName();
        String clientTel = client.getTel();
        String clientEmail = client.getEmail();
//        String clientDescription = client.getDescription();
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "tel", "tel.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty");
        ValidationUtils.rejectIfEmpty(errors, "description", "description.empty");

        if (clientTel.length() >11 || clientTel.length() < 10){
            errors.rejectValue("tel", "clientTel.length");
        }
        if (!clientTel.startsWith("0")){
            errors.rejectValue("tel", "clientTel.startsWith");
        }
        if (!clientTel.matches("(^$|[0-9]*$)")){
            errors.rejectValue("tel", "clientTel.matches");
        }
        if (!clientName.matches("^[a-zA-ZÀ-ỹ-\\s]+$")){
            errors.rejectValue("name","clientName.matches");
        }
        if (!clientEmail.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")){
            errors.rejectValue("email","clientEmail.matches");
        }
    }
}
