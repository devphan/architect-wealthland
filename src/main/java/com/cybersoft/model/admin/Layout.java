package com.cybersoft.model.admin;

import org.hibernate.annotations.Where;
import org.springframework.validation.BindingResult;

import javax.persistence.*;

@Entity
@Table(name = "layouts")
@Where(clause = "deleted = 0")
public class Layout{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String tab;
    private String description;
    private String type;
    private short deleted;



    public Layout() {
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }

    public void validate(Layout layout, BindingResult bindingResult) {
    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return Client.class.isAssignableFrom(clazz);
//    }

//    @Override
//    public void validate(Object target, Errors errors) {
//        Partner partner = (Partner) target;
//        String partnerName = partner.getName();
//        String partnerTel = partner.getTel();
//        String partnerEmail = partner.getEmail();
//        String partnerAddress = partner.getAddress();
////        String clientDescription = client.getDescription();
//        ValidationUtils.rejectIfEmpty(errors, "name", "partnerName.empty");
//        ValidationUtils.rejectIfEmpty(errors, "tel", "partnerTel.empty");
//        ValidationUtils.rejectIfEmpty(errors, "email", "partnerEmail.empty");
//        ValidationUtils.rejectIfEmpty(errors, "address", "partnerAddress.empty");
//
//        if (partnerTel.length() >11 || partnerTel.length() < 10){
//            errors.rejectValue("tel", "partnerTel.length");
//        }
//        if (!partnerTel.startsWith("0")){
//            errors.rejectValue("tel", "partnerTel.startsWith");
//        }
//        if (!partnerTel.matches("(^$|[0-9]*$)")){
//            errors.rejectValue("tel", "partnerTel.matches");
//        }
//        if (!partnerName.matches("^[a-zA-ZÀ-ỹ-\\s]+$")){
//            errors.rejectValue("name","partnerName.matches");
//        }
//        if (!partnerEmail.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")){
//            errors.rejectValue("email","partnerEmail.matches");
//        }
//    }
}
