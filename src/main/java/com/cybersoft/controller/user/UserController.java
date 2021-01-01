package com.cybersoft.controller.user;

import com.cybersoft.model.admin.*;
import com.cybersoft.service.impl.*;
import com.cybersoft.model.admin.*;
import com.cybersoft.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private LayoutServiceImpl layoutService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private EstateServiceImpl estateService;

    @Autowired
    private NewsServiceImpl newsService;

    @Autowired
    private CategoryNewServiceImpl categoryNewService;

    @Autowired
    private ContactServiceImpl contactService;

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private BannerServiceImpl bannerService;


    @PostMapping(value = "/contacts", produces = "application/json;charset=UTF-8")
    public ModelAndView addClient(HttpServletRequest request,@Valid @ModelAttribute("clients") Client client, BindingResult bindingResult) {
        String message;
        new Client().validate(client, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("fixed-user/contact");
            return modelAndView;
        } else {
            String status = "Not Done";
            client.setStatus(status);
            clientService.save(client);
            ModelAndView modelAndView = new ModelAndView("fixed-user/contact");
            message="<strong>Thanks!</strong> for your concern";
            modelAndView.addObject("error", "success");

            modelAndView.addObject("clients", new Client());
            modelAndView.addObject("message", message);
            return modelAndView;
        }
    }


    @GetMapping("/about")
    public String introduce(){
        return "fixed-user/introduce";
    }

    @GetMapping("/land")
    public String land(){
        return "fixed-user/land";
    }

    @GetMapping("/list")
    public String list(){
        return "fixed-user/list";
    }

    @GetMapping("/categoryNewss")
    public ModelAndView showNewsCategory(@RequestParam Long id){
        List<News> news = newsService.findByCategoryNews (id);
        if (news != null){
            ModelAndView modelAndView = new ModelAndView("fixed-user/blogs");
            modelAndView.addObject("news",news);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @GetMapping("/estates")
    public ModelAndView showEstateCategory(@RequestParam Long id){
        Category estateCategory = categoryService.findById (id);
        List<Estate> estates = estateService.findByCategory (id);
        if (estateCategory != null){
            ModelAndView modelAndView = new ModelAndView("fixed-user/project");
            modelAndView.addObject("estate",estateCategory);
            modelAndView.addObject("estatess",estates);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @GetMapping("/estate")
    public ModelAndView showEstateDetail(@RequestParam Long id){
        Estate estate = estateService.findById (id);
        if (estate != null){
            ModelAndView modelAndView = new ModelAndView("fixed-user/estateDetails");
            modelAndView.addObject("estate",estate);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");
        return modelAndView;
    }

    @GetMapping("/blogs")
    public String blogs(){
        return "fixed-user/blogs";
    }

    @GetMapping("/imglib")
    public String lib(){
        return "fixed-user/imglib";
    }

    @GetMapping("/search")
    public ModelAndView search(@ModelAttribute("estate") Estate estate) {
        ModelAndView modelAndView = new ModelAndView ("fixed-user/search");
        modelAndView.addObject ("estate",estate);
        return modelAndView;
    }

    @GetMapping("/contacts")
    public String contact(Model model) {
        model.addAttribute("clients", new Client());
        return "fixed-user/contact";
    }

//    @GetMapping("/contacts/add")
//    public ModelAndView showAddContact(){
//        ModelAndView modelAndView = new  ModelAndView("fixed-user/contact");
//        modelAndView.addObject("contacts", new Contact());
//        return modelAndView;
//    }

    @GetMapping("/blogDetails")
    public ModelAndView showDetail(@RequestParam Long id){
        News news = newsService.findById(id);
        if (news != null){
            ModelAndView modelAndView = new ModelAndView("fixed-user/blog-single");
            modelAndView.addObject("newss",news);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView index() {
        List<Banner> bannerList = bannerService.findAll();
        ModelAndView modelAndView = new ModelAndView ("fixed-user/index");
        modelAndView.addObject ("estate",new Estate ());
        modelAndView.addObject("banners",bannerList);
        return modelAndView;
    }

    @GetMapping("/abouts")
    public ModelAndView showDetail(@RequestParam String tab){
        Layout intro = layoutService.findByAboutTab (tab);
        if (intro != null){
            ModelAndView modelAndView = new ModelAndView("fixed-user/introduce");
            modelAndView.addObject("intro",intro);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @GetMapping("/partners")
    ModelAndView partner(@RequestParam String tab){
        Layout layout = layoutService.findByPartnerTab (tab);
        ModelAndView modelAndView = new ModelAndView ("fixed-user/partner");
        modelAndView.addObject ("partner",layout);
        return modelAndView;
    }
}
