package com.cybersoft.controller.admin;

import com.cybersoft.service.impl.BannerServiceImpl;
import com.cybersoft.model.admin.Banner;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/web")
public class BannerController extends AdminBaseController {
    @Autowired
    private BannerServiceImpl bannerService;

    private final  String TERM = "Banner";
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Banner.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    @GetMapping("/banners/")
    public String banners(Model model, Pageable pageable){
        Page<Banner> banners = bannerService.findAllPage(pageable);
        model.addAttribute("banners", banners);
        return "admin/banner/view";
    }

    @GetMapping("/banners/add")
    public ModelAndView showAddBanner(){
        ModelAndView modelAndView = new  ModelAndView("admin/banner/add");
        modelAndView.addObject("banners", new Banner());
        modelAndView.addObject("action",ACTION_ADD);
        return modelAndView;
    }

    @PostMapping("/banners/add")
    public ModelAndView addBanner(HttpServletRequest request, @ModelAttribute("banner") Banner banner){

        //
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = banner.getFileImage();
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
                        banner.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        String message;
        ModelAndView modelAndView = new ModelAndView("admin/banner/add");
        try {
            bannerService.save(banner);
            message="Add new success";
            modelAndView.addObject("error","success");
        } catch (Exception ex){
            message="Add error";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("banners",new Banner());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @GetMapping("/banners/edit/{id}")
    public ModelAndView showEditBanner(@PathVariable Long id){
        Banner banner = bannerService.findById(id);
        if (banner != null){
            ModelAndView modelAndView = new ModelAndView("admin/banner/add");
            modelAndView.addObject("banners",banner);
            modelAndView.addObject("action",ACTION_EDIT);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @GetMapping("/banners/delete/{id}")
    public ModelAndView showDeleteBanner(@PathVariable long id){
        Banner banner = bannerService.findById(id);
        if (banner != null){
            ModelAndView modelAndView = new ModelAndView("admin/banner/delete");
            modelAndView.addObject("banners",banner);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @PostMapping("/banners/edit")
    public ModelAndView editBanner(HttpServletRequest request,@ModelAttribute("banner") Banner banner){
        //
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = banner.getFileImage();
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
                        banner.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        String message;
        ModelAndView modelAndView = new ModelAndView("admin/banner/add");
        try {
            bannerService.save(banner);
            message="<strong>Success!</strong> Your banner has been update successfully..";
            modelAndView.addObject("error", "success");

        }catch (Exception ex){
            message="<strong>Error!</strong> Your banner has been update error..";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("banners",banner);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping("/banners/delete")
    public ModelAndView deleteCity(@ModelAttribute Banner banner){
        String message;
        try {
            bannerService.remove(banner.getId());
            return new ModelAndView("redirect:/web/banners/");
        }catch (Exception ex){
            message="<strong>Error!</strong> Your city has been update error..";

            ModelAndView modelAndView = new ModelAndView("admin/banner/delete");
            modelAndView.addObject("error", "error");
            modelAndView.addObject("banners",banner);
            modelAndView.addObject("message", message);
            return modelAndView;

        }
    }

//    @GetMapping("/banners/detail/{id}")
//    public ModelAndView showDetail(@PathVariable Long id){
//        Banner banner = bannerService.findById(id);
//        if (banner != null){
//            ModelAndView modelAndView = new ModelAndView("/admin/banner/detail");
//            modelAndView.addObject("banners",banner);
//            return modelAndView;
//        }
//
//        ModelAndView modelAndView = new ModelAndView("/error/404");
//        modelAndView.addObject("message","Data not found !");
//
//        return modelAndView;
//    }
}

