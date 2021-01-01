package com.cybersoft.model.admin;

import org.hibernate.annotations.Where;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
@Where(clause = "deleted = 0")
public class Contact implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String tel;
    private String address;
    private short deleted;

    public Contact() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Contact.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Contact contact = (Contact) target;
        String contactName = contact.getName();

        String contactEmail = contact.getEmail();

//        String clientDescription = client.getDescription();
        ValidationUtils.rejectIfEmpty(errors, "name", "contactName.empty");

        ValidationUtils.rejectIfEmpty(errors, "email", "contactEmail.empty");
        ValidationUtils.rejectIfEmpty(errors, "tel", "contactTel.empty");



        if (!contactName.matches("^[a-zA-ZÀ-ỹ-\\s]+$")){
            errors.rejectValue("name","contactName.matches");
        }
        if (!contactEmail.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")){
            errors.rejectValue("email","contactEmail.matches");
        }
    }
}
