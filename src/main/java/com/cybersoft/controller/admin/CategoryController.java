package com.cybersoft.controller.admin;

import com.cybersoft.service.impl.CategoryServiceImpl;
import com.cybersoft.model.admin.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/web")
public class CategoryController extends AdminBaseController {
    @Autowired
    private CategoryServiceImpl categoryService;

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
    @GetMapping("/categorys/")
    public String categorys(Model model, Pageable pageable){
        Page<Category> categorys = categoryService.findAllPage(pageable);
        model.addAttribute("categorys", categorys);
        return "admin/category/view";
    }

    @GetMapping("/categorys/add")
    public ModelAndView showAddCategory(){
        ModelAndView modelAndView = new  ModelAndView("admin/category/add");
        modelAndView.addObject("categorys", new Category());
        modelAndView.addObject("action",ACTION_ADD);
        return modelAndView;
    }

    @PostMapping("/categorys/add")
    public ModelAndView addCategory(HttpServletRequest request, @ModelAttribute("category") Category category){

        String message;
        ModelAndView modelAndView = new ModelAndView("admin/category/add");
        try {
            categoryService.save(category);
            message="Add new success";
            modelAndView.addObject("error","success");
        } catch (Exception ex){
            message="Add error";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("categorys",new Category());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @GetMapping("/categorys/edit/{id}")
    public ModelAndView showEditCategory(@PathVariable Long id){
        Category category = categoryService.findById(id);
        if (category != null){
            ModelAndView modelAndView = new ModelAndView("admin/category/add");
            modelAndView.addObject("categorys",category);
            modelAndView.addObject("action",ACTION_EDIT);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @GetMapping("/categorys/delete/{id}")
    public ModelAndView showDeleteCategory(@PathVariable long id){
        Category category = categoryService.findById(id);
        if (category != null){
            ModelAndView modelAndView = new ModelAndView("admin/category/delete");
            modelAndView.addObject("categorys",category);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @PostMapping("/categorys/edit")
    public ModelAndView editCategory(HttpServletRequest request,@ModelAttribute("category") Category category){
        String message;
        ModelAndView modelAndView = new ModelAndView("admin/category/add");
        try {
            categoryService.save(category);
            message="<strong>Success!</strong> Your category has been update successfully..";
            modelAndView.addObject("error", "success");

        }catch (Exception ex){
            message="<strong>Error!</strong> Your category has been update error..";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("categorys",category);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping("/categorys/delete")
    public ModelAndView deleteCity(@ModelAttribute Category category){
        String message;
        try {
            categoryService.remove(category.getId());
            return new ModelAndView("redirect:web/categorys/");
        }catch (Exception ex){
            message="<strong>Error!</strong> Your city has been update error..";

            ModelAndView modelAndView = new ModelAndView("admin/category/delete");
            modelAndView.addObject("error", "error");
            modelAndView.addObject("categorys",category);
            modelAndView.addObject("message", message);
            return modelAndView;

        }
    }
}

