package com.yad.sjjg.repo.controller;

import com.yad.sjjg.repo.model.*;
import com.yad.sjjg.repo.service.AllService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class indexController {
    @Autowired
    private AllService allService;

    @GetMapping("/add")
    public  String  addgoods(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        model.addAttribute("account",user);
        return  "add";
    }

    @GetMapping("/build")
    public  String buid(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        model.addAttribute("account",user);
        return  "build";
    }
    @GetMapping("/default")
    public  String defaults(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        if (user!=null){
            DateDispalyDto dateDispalyDto=allService.Datedisplay(user);
            model.addAttribute("display",dateDispalyDto);
        }
        return "default";
    }
    @GetMapping("/oldall")
    public  String oldall(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        if (user!=null){
            List<Goods> list = allService.getGoods(user);
            model.addAttribute("account",user);
            model.addAttribute("list",list);
            return  "all";
        }
        return  "login";
    }
    @GetMapping("/record")
    public  String getRecord(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        if (user!=null){
            List<RecordDao> list = allService.getRecords(user);
            model.addAttribute("records",list);
            return  "record";
        }
        return  "login";
    }

}
