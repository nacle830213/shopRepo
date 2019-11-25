package com.yad.sjjg.repo.controller;

import com.yad.sjjg.repo.model.User;
import com.yad.sjjg.repo.service.AllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;

@Controller
public class UserController {
    @Autowired
    private AllService allService;
    @GetMapping("/")
    public  String login(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        String account =(String) session.getAttribute("user");
        if (account==null)
            return "login";
        model.addAttribute("account",account);
        return  "index";
    }
    @PostMapping("/index")
    public String  logined(@RequestParam String account, @RequestParam String password,
                           HttpServletRequest request, Model model){
        User user= allService.logined(account,password);
        if (user!=null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user.getAccount());
            session.setAttribute("id",user.getId());
            model.addAttribute("account",user.getAccount());
            return "index";
        }
        model.addAttribute("error","错误");
        return "login";
    }
    @GetMapping("/index")
    public  String index(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("user");
        if (user!=null){
            model.addAttribute("account",user);
            return  "index";
        }
        return  "login";
    }
    @PostMapping("/signup")
    public  String signup(@RequestParam String account, @RequestParam String password,
                          HttpServletRequest request, Model model){
        User user=allService.signup(account,password);
        HttpSession session = request.getSession();
        session.setAttribute("user", account);
        session.setAttribute("id",user.getId());
        return "index";
    }

}
