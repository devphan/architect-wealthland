package com.cybersoft.controller.admin;

import com.cybersoft.service.impl.BankServiceImpl;
import com.cybersoft.model.admin.Bank;
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
public class BankController extends AdminBaseController {
    @Autowired
    private BankServiceImpl bankService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Bank.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    @GetMapping("/banks/")
    public String banks(Model model, Pageable pageable){
        Page<Bank> banks = bankService.findAllPage(pageable);
        model.addAttribute("banks", banks);
        return "/admin/bank/view";
    }

    @GetMapping("/banks/add")
    public ModelAndView showAddBank(){
        ModelAndView modelAndView = new  ModelAndView("/admin/bank/add");
        modelAndView.addObject("banks", new Bank());
        modelAndView.addObject("action",ACTION_ADD);
        return modelAndView;
    }

    @PostMapping("/banks/add")
    public ModelAndView addBank(HttpServletRequest request, @ModelAttribute("bank") Bank bank){

        //
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = bank.getFileImage();
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
                        bank.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        String message;
        ModelAndView modelAndView = new ModelAndView("/admin/bank/add");
        try {
            bankService.save(bank);
            message="Add new success";
            modelAndView.addObject("error","success");
        } catch (Exception ex){
            message="Add error";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("banks",new Bank());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @GetMapping("/banks/edit/{id}")
    public ModelAndView showEditBank(@PathVariable Long id){
        Bank bank = bankService.findById(id);
        if (bank != null){
            ModelAndView modelAndView = new ModelAndView("/admin/bank/add");
            modelAndView.addObject("banks",bank);
            modelAndView.addObject("action",ACTION_EDIT);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }
    @GetMapping("/banks/delete/{id}")
    public ModelAndView showDeleteBank(@PathVariable long id){
        Bank bank = bankService.findById(id);
        if (bank != null){
            ModelAndView modelAndView = new ModelAndView("/admin/bank/delete");
            modelAndView.addObject("banks",bank);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

    @PostMapping("/banks/edit")
    public ModelAndView editBank(HttpServletRequest request,@ModelAttribute("bank") Bank bank){
        //
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = bank.getFileImage();
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
                        bank.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        String message;
        ModelAndView modelAndView = new ModelAndView("/admin/bank/add");
        try {
            bankService.save(bank);
            message="<strong>Success!</strong> Your bank has been update successfully..";
            modelAndView.addObject("error", "success");

        }catch (Exception ex){
            message="<strong>Error!</strong> Your bank has been update error..";
            modelAndView.addObject("error", "error");
        }
        modelAndView.addObject("banks",bank);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @PostMapping("/banks/delete")
    public ModelAndView deleteCity(@ModelAttribute Bank bank){
        String message;
        try {
            bankService.remove(bank.getId());
            return new ModelAndView("redirect:/web/banks/");
        }catch (Exception ex){
            message="<strong>Error!</strong> Your city has been update error..";

            ModelAndView modelAndView = new ModelAndView("/admin/bank/delete");
            modelAndView.addObject("error", "error");
            modelAndView.addObject("banks",bank);
            modelAndView.addObject("message", message);
            return modelAndView;

        }
    }

    @GetMapping("/banks/detail/{id}")
    public ModelAndView showDetail(@PathVariable Long id){
        Bank bank = bankService.findById(id);
        if (bank != null){
            ModelAndView modelAndView = new ModelAndView("/admin/bank/detail");
            modelAndView.addObject("banks",bank);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/error/404");
        modelAndView.addObject("message","Data not found !");

        return modelAndView;
    }

}
