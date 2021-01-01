package com.cybersoft.controller.admin;

import com.cybersoft.model.admin.Layout;
import com.cybersoft.service.impl.LayoutServiceImpl;
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
public class LayoutController extends AdminBaseController {
    @Autowired
    private LayoutServiceImpl layoutService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Layout.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    @GetMapping("/layouts/")
    public String layouts(Model model, Pageable pageable){
        Page<Layout> layouts = layoutService.findAllPage(pageable);
        model.addAttribute("layouts", layouts);
        return "admin/layout/view";
    }


    @GetMapping("/layouts/add")
    public ModelAndView showAddLayout(){
        ModelAndView modelAndView = new  ModelAndView("admin/layout/add");
        modelAndView.addObject("layouts", new Layout());
        modelAndView.addObject("action",ACTION_ADD);
        return modelAndView;
    }

    @PostMapping("/layouts/add")
    public ModelAndView addLayout(HttpServletRequest request, @ModelAttribute("layout") Layout layout){

        String message;
            layoutService.save(layout);
            ModelAndView modelAndView = new ModelAndView("admin/layout/add");
            message="<strong>Success!</strong> Your layout has been add successfully..";
            modelAndView.addObject("error", "success");
            modelAndView.addObject("layouts", new Layout());
            modelAndView.addObject("action", ACTION_ADD);
            modelAndView.addObject("message", message);
            return modelAndView;
    }

    @GetMapping("/layouts/edit/{id}")
    public ModelAndView showEditLayout(@PathVariable Long id){
        Layout layout = layoutService.findById(id);
        if (layout != null){
            ModelAndView modelAndView = new ModelAndView("admin/layout/add");
            modelAndView.addObject("layouts",layout);
            modelAndView.addObject("action",ACTION_EDIT);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @GetMapping("/layouts/delete/{id}")
    public ModelAndView showDeleteLayout(@PathVariable long id){
        Layout layout = layoutService.findById(id);
        if (layout != null){
            ModelAndView modelAndView = new ModelAndView("admin/layout/delete");
            modelAndView.addObject("layouts",layout);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @PostMapping("/layouts/edit")
    public ModelAndView editLayout(HttpServletRequest request, @ModelAttribute("layout") Layout layout){
        //

        String message;
            layoutService.save(layout);
            ModelAndView modelAndView = new ModelAndView("admin/layout/add");
            message="<strong>Success!</strong> Your layout has been update successfully..";
            modelAndView.addObject("error", "success");
            modelAndView.addObject("layouts", layout);
            modelAndView.addObject("action", ACTION_EDIT);
            modelAndView.addObject("message", message);
            return modelAndView;
    }

    @PostMapping("/layouts/delete")
    public ModelAndView deleteCity(@ModelAttribute Layout layout){
        String message;
        try {
            layoutService.remove(layout.getId());
            return new ModelAndView("redirect:/web/layouts/");
        }catch (Exception ex){
            message="<strong>Error!</strong> Your city has been update error..";

            ModelAndView modelAndView = new ModelAndView("admin/layout/delete");
            modelAndView.addObject("error", "error");
            modelAndView.addObject("layouts",layout);
            modelAndView.addObject("message", message);
            return modelAndView;

        }
    }

    @GetMapping("/layouts/detail/{id}")
    public ModelAndView showDetail(@PathVariable Long id){
        Layout layout = layoutService.findById(id);
        if (layout != null){
            ModelAndView modelAndView = new ModelAndView("admin/layout/detail");
            modelAndView.addObject("layouts",layout);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

}
