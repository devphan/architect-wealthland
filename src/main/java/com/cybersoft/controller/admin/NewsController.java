package com.cybersoft.controller.admin;

import com.cybersoft.model.admin.Estate;
import com.cybersoft.model.admin.Project;
import com.cybersoft.service.impl.EstateServiceImpl;
import com.cybersoft.service.impl.NewsServiceImpl;
import com.cybersoft.service.impl.ProjectServiceImpl;
import com.cybersoft.model.admin.*;
import com.cybersoft.model.admin.News;
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
public class NewsController extends AdminBaseController {
    @Autowired
    private NewsServiceImpl newsService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == News.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    @GetMapping("/news/")
    public String news(Model model, Pageable pageable){
        Page<News> news = newsService.findAllPage(pageable);
        model.addAttribute("newss", news);
        return "admin/news/view";
    }

    @GetMapping("/news/add")
    public ModelAndView showAddNews(){
        ModelAndView modelAndView = new  ModelAndView("admin/news/add");
        modelAndView.addObject("newss", new News());
        modelAndView.addObject("action",ACTION_ADD);
        return modelAndView;
    }

    @PostMapping("/news/add")
    public ModelAndView addNews(HttpServletRequest request, @ModelAttribute("news") News news){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
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
        CommonsMultipartFile[] fileDatas = news.getFileImage();
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
                        news.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        String message;
        ModelAndView modelAndView = new ModelAndView("admin/news/add");
        try {
            news.setCheckin (dtf.format(now));
            news.setDate(day.format(now));
            news.setMonth ("Th"+month.format(now));
            newsService.save(news);
            message="Add new success";
            modelAndView.addObject("error","success");
        } catch (Exception ex){
            message="Add error";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("newss",new News());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @GetMapping("/news/edit/{id}")
    public ModelAndView showEditNews(@PathVariable Long id){
        News news = newsService.findById(id);
        if (news != null){
            ModelAndView modelAndView = new ModelAndView("admin/news/add");
            modelAndView.addObject("newss",news);
            modelAndView.addObject("action",ACTION_EDIT);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @GetMapping("/news/delete/{id}")
    public ModelAndView showDeleteNews(@PathVariable long id){
        News news = newsService.findById(id);
        if (news != null){
            ModelAndView modelAndView = new ModelAndView("admin/news/delete");
            modelAndView.addObject("newss",news);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @PostMapping("/news/edit")
    public ModelAndView editNews(HttpServletRequest request,@ModelAttribute("news") News news){
        //
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = news.getFileImage();
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
                        news.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        String message;
        ModelAndView modelAndView = new ModelAndView("admin/news/add");
        try {
            news.getCheckin();
            news.getDate();
            news.getMonth();
            newsService.save(news);
            message="<strong>Success!</strong> Your news has been update successfully..";
            modelAndView.addObject("error", "success");

        }catch (Exception ex){
            message="<strong>Error!</strong> Your news has been update error..";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("newss",news);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping("/news/delete")
    public ModelAndView deleteCity(@ModelAttribute News news){
        String message;
        try {
            newsService.remove(news.getId());
            return new ModelAndView("redirect:/web/news/");
        }catch (Exception ex){
            message="<strong>Error!</strong> Your city has been update error..";

            ModelAndView modelAndView = new ModelAndView("admin/news/delete");
            modelAndView.addObject("error", "error");
            modelAndView.addObject("newss",news);
            modelAndView.addObject("message", message);
            return modelAndView;

        }
    }

    @GetMapping("/news/detail/{id}")
    public ModelAndView showDetail(@PathVariable Long id){
        News news = newsService.findById(id);
        if (news != null){
            ModelAndView modelAndView = new ModelAndView("admin/news/detail");
            modelAndView.addObject("newss",news);
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
    private EstateServiceImpl estateService;
    @ModelAttribute("estates")
    public List<Estate> estates() {return estateService.findAll();}
}

