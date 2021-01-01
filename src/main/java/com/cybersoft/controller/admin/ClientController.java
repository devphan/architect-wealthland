package com.cybersoft.controller.admin;

import com.cybersoft.service.impl.ClientServiceImpl;
import com.cybersoft.model.admin.Client;
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
public class ClientController extends AdminBaseController {
    @Autowired
    private ClientServiceImpl clientService;

    private final  String TERM = "Client";
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
        if (target.getClass() == Client.class) {
            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }
    @GetMapping("/clients/")
    public String clients(Model model, Pageable pageable){
        Page<Client> clients = clientService.findAllPage(pageable);
        model.addAttribute("clients", clients);
        return "admin/client/view";
    }

    @GetMapping("/clients/add")
    public ModelAndView showAddClient(){
        ModelAndView modelAndView = new  ModelAndView("admin/client/add");
        modelAndView.addObject("clients", new Client());
        modelAndView.addObject("action",ACTION_ADD);
        return modelAndView;
    }

    @PostMapping(value = "/clients/add", produces = "application/json;charset=UTF-8")
    public ModelAndView addClient(HttpServletRequest request,@Valid @ModelAttribute("clients") Client client, BindingResult bindingResult) {
        String message;
        new Client().validate(client, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("admin/client/add");
            return modelAndView;
        } else {
            clientService.save(client);
            ModelAndView modelAndView = new ModelAndView("admin/client/add");
            message="<strong>Success!</strong> Your client has been add successfully..";
            modelAndView.addObject("error", "success");

            modelAndView.addObject("clients", new Client());
            modelAndView.addObject("action", ACTION_ADD);
            modelAndView.addObject("message", message);
            return modelAndView;
        }
    }

    @GetMapping("/clients/edit/{id}")
    public ModelAndView showEditClient(@PathVariable Long id){
        Client client = clientService.findById(id);
        if (client != null){
            ModelAndView modelAndView = new ModelAndView("admin/client/edit");
            modelAndView.addObject("clients",client);
            modelAndView.addObject("action",ACTION_EDIT);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @GetMapping("/clients/delete/{id}")
    public ModelAndView showDeleteClient(@PathVariable long id){
        Client client = clientService.findById(id);
        if (client != null){
            ModelAndView modelAndView = new ModelAndView("admin/client/delete");
            modelAndView.addObject("clients",client);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @PostMapping("/clients/edit")
    public ModelAndView editClient(HttpServletRequest request, @Valid @ModelAttribute("clients") Client client, BindingResult bindingResult){
        String message;
        new Client().validate(client, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("admin/client/edit");
            return modelAndView;
        } else {
            clientService.save(client);
            ModelAndView modelAndView = new ModelAndView("admin/client/edit");
            message="<strong>Success!</strong> Your client has been update successfully..";
            modelAndView.addObject("error", "success");

            modelAndView.addObject("clients", client);
            modelAndView.addObject("action", ACTION_ADD);
            modelAndView.addObject("message", message);
            return modelAndView;
        }
    }

    @PostMapping("/clients/delete")
    public ModelAndView deleteCity(@ModelAttribute Client client){
        String message;
        try {
            clientService.remove(client.getId());
            return new ModelAndView("redirect:/web/clients/");
        }catch (Exception ex){
            message="<strong>Error!</strong> Your city has been update error..";

            ModelAndView modelAndView = new ModelAndView("admin/client/delete");
            modelAndView.addObject("error", "error");
            modelAndView.addObject("clients",client);
            modelAndView.addObject("message", message);
            return modelAndView;

        }
    }
}

