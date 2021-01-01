package com.cybersoft.controller.admin;

import com.cybersoft.service.impl.ContactServiceImpl;
import com.cybersoft.model.admin.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/web")
public class ContactController extends AdminBaseController {
    @Autowired
    private ContactServiceImpl contactService;

    private final  String TERM = "Contact";
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
        if (target.getClass() == Contact.class) {
            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }
    @GetMapping("/contacts/")
    public String contacts(Model model, Pageable pageable){
        Page<Contact> contacts = contactService.findAllPage(pageable);
        model.addAttribute("contacts", contacts);
        return "/admin/contact/view";
    }

    @GetMapping("/contacts/add")
    public ModelAndView showAddContact(){
        ModelAndView modelAndView = new  ModelAndView("admin/contact/add");
        modelAndView.addObject("contacts", new Contact());
        modelAndView.addObject("action",ACTION_ADD);
        return modelAndView;
    }

    @PostMapping("/contacts/add")
    public ModelAndView addContact(HttpServletRequest request, @Valid @ModelAttribute("contact") Contact contact, BindingResult bindingResult){

        String message;
        new Contact().validate(contact, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("admin/contact/add");
            return modelAndView;
        } else {
            contactService.save(contact);
            ModelAndView modelAndView = new ModelAndView("admin/contact/add");
            message="<strong>Success!</strong> Your contact has been add successfully..";
            modelAndView.addObject("error", "success");

            modelAndView.addObject("contacts", new Contact());
            modelAndView.addObject("action", ACTION_ADD);
            modelAndView.addObject("message", message);
            return modelAndView;
        }
    }

    @GetMapping("/contacts/edit/{id}")
    public ModelAndView showEditContact(@PathVariable Long id){
        Contact contact = contactService.findById(id);
        if (contact != null){
            ModelAndView modelAndView = new ModelAndView("admin/contact/edit");
            modelAndView.addObject("contacts",contact);
//            modelAndView.addObject("action",ACTION_EDIT);
            return modelAndView;
        }
        else {
            ModelAndView modelAndView = new ModelAndView("error/404");
            modelAndView.addObject("message", "Data not found !");

            return modelAndView;
        }
    }
    @GetMapping("/contacts/delete/{id}")
    public ModelAndView showDeleteContact(@PathVariable long id){
        Contact contact = contactService.findById(id);
        if (contact != null){
            ModelAndView modelAndView = new ModelAndView("admin/contact/delete");
            modelAndView.addObject("contacts",contact);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @PostMapping("/contacts/edit")
    public ModelAndView editContact(HttpServletRequest request,@Valid @ModelAttribute("contact") Contact contact,BindingResult bindingResult){
        String message;
        new Contact().validate(contact, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("admin/contact/edit");
            return modelAndView;
        } else {
            contactService.save(contact);
            ModelAndView modelAndView = new ModelAndView("admin/contact/edit");
            message="<strong>Success!</strong> Your contact has been update successfully..";
            modelAndView.addObject("error", "success");
            modelAndView.addObject("contacts", contact);
            modelAndView.addObject("message", message);
            return modelAndView;
        }
    }

    @PostMapping("/contacts/delete")
    public ModelAndView deleteCity(@ModelAttribute Contact contact){
        String message;
        try {
            contactService.remove(contact.getId());
            return new ModelAndView("redirect:/web/contacts/");
        }catch (Exception ex){
            message="<strong>Error!</strong> Your city has been update error..";

            ModelAndView modelAndView = new ModelAndView("admin/contact/delete");
            modelAndView.addObject("error", "error");
            modelAndView.addObject("contacts",contact);
            modelAndView.addObject("message", message);
            return modelAndView;

        }
    }

}

