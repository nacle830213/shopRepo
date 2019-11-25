package com.yad.sjjg.repo.controller;

import com.yad.sjjg.repo.model.Goods;
import com.yad.sjjg.repo.model.GoodsDto;
import com.yad.sjjg.repo.model.LinkList;
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

    @PostMapping("/add")
    public  String  addgoods(@RequestBody List<GoodsDto> list){
        for(GoodsDto goodsDto : list){
            System.out.println(goodsDto);
        }
        return  "succsee";
    }

    @GetMapping("/build")
    public  String buid(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        model.addAttribute("account",user);
        return  "build";
    }
    @GetMapping("/default")
    public  String defaults(){
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

}
