package com.cybersoft.controller.admin;

import com.cybersoft.model.admin.Bank;
import com.cybersoft.model.admin.Estate;
import com.cybersoft.model.admin.Project;
import com.cybersoft.service.impl.*;
import com.cybersoft.model.City;
import com.cybersoft.model.Province;
import com.cybersoft.model.admin.*;
import com.cybersoft.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web")
public class EstateController extends AdminBaseController {
    @Autowired
    private EstateServiceImpl estateService;

    private final  String TERM = "Estate";
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Estate.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    @GetMapping("/estates/")
    public String estates(Model model, Pageable pageable){
        Page<Estate> estates = estateService.findAllPage(pageable);
        model.addAttribute("estates", estates);
        return "admin/estate/view";
    }

    @GetMapping("/estates/add")
    public ModelAndView showAddEstate(){
        ModelAndView modelAndView = new  ModelAndView("admin/estate/add");
        modelAndView.addObject("estates", new Estate());
        modelAndView.addObject("action",ACTION_ADD);
        return modelAndView;
    }

    @PostMapping("/estates/add")
    public ModelAndView addEstate(HttpServletRequest request, @ModelAttribute("estate") Estate estate){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();


        //
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = estate.getFileImage();
        //
        if (fileDatas!=null){
            Map<File, String> uploadedFiles = new HashMap();
            for (CommonsMultipartFile fileData : fileDatas) {

                // Tên file gốc tại Client.
                String name = fileData.getOriginalFilename();
                System.out.println("Client File Name = " + name);

                if (name != null && name.length() > 0) {
                    try {
                        // Tạo file tại Server.
                        File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                        // Luồng ghi dữ liệu vào file trên Server.
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                        stream.write(fileData.getBytes());
                        stream.close();
                        //
                        estate.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        String message;
        ModelAndView modelAndView = new ModelAndView("admin/estate/add");
        try {
            estate.setCheckin (dtf.format(now));
            estateService.save(estate);
            message="Add new success";
            modelAndView.addObject("error","success");
        } catch (Exception ex){
            message="Add error";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("estates",new Estate());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @GetMapping("/estates/edit/{id}")
    public ModelAndView showEditEstate(@PathVariable Long id){
        Estate estate = estateService.findById(id);
        if (estate != null){
            ModelAndView modelAndView = new ModelAndView("admin/estate/add");
            modelAndView.addObject("estates",estate);
            modelAndView.addObject("action",ACTION_EDIT);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @GetMapping("/estates/delete/{id}")
    public ModelAndView showDeleteEstate(@PathVariable long id){
        Estate estate = estateService.findById(id);
        if (estate != null){
            ModelAndView modelAndView = new ModelAndView("admin/estate/delete");
            modelAndView.addObject("estates",estate);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @PostMapping("/estates/edit")
    public ModelAndView editEstate(HttpServletRequest request,@ModelAttribute("estate") Estate estate){
        //
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = estate.getFileImage();
        //
        if (fileDatas!=null){
            Map<File, String> uploadedFiles = new HashMap();
            for (CommonsMultipartFile fileData : fileDatas) {

                // Tên file gốc tại Client.
                String name = fileData.getOriginalFilename();
                System.out.println("Client File Name = " + name);

                if (name != null && name.length() > 0) {
                    try {
                        // Tạo file tại Server.
                        File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                        // Luồng ghi dữ liệu vào file trên Server.
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                        stream.write(fileData.getBytes());
                        stream.close();
                        //
                        estate.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        String message;
        ModelAndView modelAndView = new ModelAndView("admin/estate/add");
        try {
            estateService.save(estate);
            message="<strong>Success!</strong> Your estate has been update successfully..";
            modelAndView.addObject("error", "success");

        }catch (Exception ex){
            message="<strong>Error!</strong> Your estate has been update error..";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("estates",estate);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping("/estates/delete")
    public ModelAndView deleteCity(@ModelAttribute Estate estate){
        String message;
        try {
            estateService.remove(estate.getId());
            return new ModelAndView("redirect:/web/estates/");
        }catch (Exception ex){
            message="<strong>Error!</strong> Your city has been update error..";

            ModelAndView modelAndView = new ModelAndView("admin/estate/delete");
            modelAndView.addObject("error", "error");
            modelAndView.addObject("estates",estate);
            modelAndView.addObject("message", message);
            return modelAndView;

        }
    }

    @GetMapping("/estates/detail/{id}")
    public ModelAndView showDetail(@PathVariable Long id){
        Estate estate = estateService.findById(id);
        if (estate != null){
            ModelAndView modelAndView = new ModelAndView("admin/estate/detail");
            modelAndView.addObject("estates",estate);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @Autowired
    private ProjectServiceImpl projectService;
    @ModelAttribute("projects")
    public List<Project> projects() {return projectService.findAll();}

    @Autowired
    private BankServiceImpl bankService;
    @ModelAttribute("banks")
    public List<Bank> banks() {return bankService.findAll();}

    @Autowired
    private ProvinceServiceImpl provinceService;
    @ModelAttribute("provinces")
    public List<Province> provinces() {return provinceService.findAll();}

    @Autowired
    private CityServiceImpl cityService;
    @ModelAttribute("cities")
    public List<City> cities() {return cityService.findAll();}
}

