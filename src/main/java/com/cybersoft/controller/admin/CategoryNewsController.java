package com.cybersoft.controller.admin;

import com.cybersoft.model.admin.Category;
import com.cybersoft.model.admin.Category_News;
import com.cybersoft.service.impl.CategoryNewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/web")
public class CategoryNewsController extends AdminBaseController {
    @Autowired
    private CategoryNewServiceImpl categoryNewService;

    private final  String TERM = "Category";
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);
        if (target.getClass() == Category.class) {
            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }
    @GetMapping("/categoryNews/")
    public String categoryNews(Model model){
        List<Category_News> categoryNews = categoryNewService.findAll();
        model.addAttribute("categoryNews", categoryNews);
        return "admin/categoryNews/view";
    }

    @GetMapping("/categoryNews/add")
    public ModelAndView showAddCategoryNews(){
        ModelAndView modelAndView = new  ModelAndView("admin/categoryNews/add");
        modelAndView.addObject("categoryNews", new Category_News());
        modelAndView.addObject("action",ACTION_ADD);
        return modelAndView;
    }

    @PostMapping("/categoryNews/add")
    public ModelAndView addCategoryNews(HttpServletRequest request, @ModelAttribute("categoryNews") Category_News category_news){

        String message;
        ModelAndView modelAndView = new ModelAndView("admin/categoryNews/add");
        try {
            categoryNewService.save(category_news);
            message="Add new category success";
            modelAndView.addObject("error","success");
        } catch (Exception ex){
            message="Add error";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("categoryNews",new Category_News());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @GetMapping("/categoryNews/edit/{id}")
    public ModelAndView showEditCategory(@PathVariable Long id){
        Category_News category_news = categoryNewService.findById(id);
        if (category_news != null){
            ModelAndView modelAndView = new ModelAndView("admin/categoryNews/add");
            modelAndView.addObject("categoryNews",category_news);
            modelAndView.addObject("action",ACTION_EDIT);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @GetMapping("/categoryNews/delete/{id}")
    public ModelAndView showDeleteCategory(@PathVariable long id){
        Category_News category_news = categoryNewService.findById(id);
        if (category_news != null){
            ModelAndView modelAndView = new ModelAndView("admin/categoryNews/delete");
            modelAndView.addObject("categoryNews",category_news);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @PostMapping("/categoryNews/edit")
    public ModelAndView editCategory(HttpServletRequest request,@ModelAttribute("categoryNews") Category_News category_news){
        String message;
        ModelAndView modelAndView = new ModelAndView("admin/categoryNews/add");
        try {
            categoryNewService.save(category_news);
            message="<strong>Success!</strong> Your category has been update successfully..";
            modelAndView.addObject("error", "success");

        }catch (Exception ex){
            message="<strong>Error!</strong> Your category has been update error..";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("categoryNews",category_news);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping("/categoryNews/delete")
    public ModelAndView deleteCity(@ModelAttribute Category_News category_news){
        String message;
        try {
            categoryNewService.remove(category_news.getId());
            return new ModelAndView("redirect:/web/categoryNews/");
        }catch (Exception ex){
            message="<strong>Error!</strong> Your city has been update error..";

            ModelAndView modelAndView = new ModelAndView("admin/categoryNews/delete");
            modelAndView.addObject("error", "error");
            modelAndView.addObject("categoryNews",category_news);
            modelAndView.addObject("message", message);
            return modelAndView;

        }
    }
}
