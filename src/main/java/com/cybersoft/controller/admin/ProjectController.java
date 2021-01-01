package com.cybersoft.controller.admin;

import com.cybersoft.model.admin.Category;
import com.cybersoft.model.admin.Project;

import com.cybersoft.service.impl.CategoryServiceImpl;
import com.cybersoft.service.impl.ProjectServiceImpl;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web")
public class ProjectController extends AdminBaseController {
    @Autowired
    private ProjectServiceImpl projectService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Project.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    @GetMapping("/projects/")
    public String projects(Model model, Pageable pageable){
        Page<Project> projects = projectService.findAllPage(pageable);
        model.addAttribute("projects", projects);
        return "/admin/project/view";
    }

    @GetMapping("/projects/add")
    public ModelAndView showAddProject(){
        ModelAndView modelAndView = new  ModelAndView("/admin/project/create");
        modelAndView.addObject("projects", new Project());
        modelAndView.addObject("action",ACTION_ADD);
        return modelAndView;
    }

    @PostMapping("/projects/add")
    public ModelAndView addProject(HttpServletRequest request, @ModelAttribute("project") Project project){

        //
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = project.getFileImage();
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
                        project.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        String message;
        ModelAndView modelAndView = new ModelAndView("/admin/project/create");
        try {
            projectService.save(project);
            message="Add new success";
            modelAndView.addObject("error","success");
        } catch (Exception ex){
            message="Add error";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("projects",new Project());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @GetMapping("/projects/edit/{id}")
    public ModelAndView showEditProject(@PathVariable Long id){
        Project project = projectService.findById(id);
        if (project != null){
            ModelAndView modelAndView = new ModelAndView("/admin/project/create");
            modelAndView.addObject("projects",project);
            modelAndView.addObject("action",ACTION_EDIT);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @GetMapping("/projects/delete/{id}")
    public ModelAndView showDeleteProject(@PathVariable long id){
        Project project = projectService.findById(id);
        if (project != null){
            ModelAndView modelAndView = new ModelAndView("/admin/project/delete");
            modelAndView.addObject("projects",project);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @PostMapping("/projects/edit")
    public ModelAndView editProject(HttpServletRequest request,@ModelAttribute("project") Project project){
        //
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = project.getFileImage();
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
                        project.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        String message;
        ModelAndView modelAndView = new ModelAndView("/admin/project/create");
        try {
            projectService.save(project);
            message="<strong>Success!</strong> Your project has been update successfully..";
            modelAndView.addObject("error", "success");

        }catch (Exception ex){
            message="<strong>Error!</strong> Your project has been update error..";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("projects",project);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping("/projects/delete")
    public ModelAndView deleteCity(@ModelAttribute Project project){
        String message;
        try {
            projectService.remove(project.getId());
            return new ModelAndView("redirect:/web/projects/");
        }catch (Exception ex){
            message="<strong>Error!</strong> Your city has been update error..";

            ModelAndView modelAndView = new ModelAndView("/admin/project/delete");
            modelAndView.addObject("error", "error");
            modelAndView.addObject("projects",project);
            modelAndView.addObject("message", message);
            return modelAndView;

        }
    }

    @GetMapping("/projects/detail/{id}")
    public ModelAndView showDetail(@PathVariable Long id){
        Project project = projectService.findById(id);
        if (project != null){
            ModelAndView modelAndView = new ModelAndView("/admin/project/detail");
            modelAndView.addObject("projects",project);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @Autowired
    private CategoryServiceImpl  categoryService;
    @ModelAttribute("categorys")
    public List<Category> categories() {return categoryService.findAll();}
}
