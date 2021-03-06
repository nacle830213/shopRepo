package com.yad.sjjg.repo.controller;

import com.yad.sjjg.repo.model.GoodsDto;
import com.yad.sjjg.repo.service.AllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class jsonController {
    @Autowired
    private AllService allService;
    @PostMapping("/build")
    public  String build (@RequestBody List<GoodsDto> list,
                          HttpServletRequest request,
                          @RequestParam String account){
        System.out.println("build");
        if (account!=null){
            allService.build(list,account);
//            model.addAttribute("list", AllService.userpool.get(account));
            System.out.println(account);
            return  "index";
        }
        return  "login";
    }
    @PostMapping("/add")
    public  String add (@RequestBody List<GoodsDto> list,
                          HttpServletRequest request,
                          @RequestParam String account){
        System.out.println("build");
        if (account!=null){
            allService.add(list,account);
            System.out.println(account);
            return  "add";
        }
        return  "login";
    }
    @GetMapping("/update")
    public String update(@RequestParam Integer id,@RequestParam Integer amount ,
                         @RequestParam Integer type,@RequestParam Double price,
                         HttpServletRequest request){
        HttpSession session=request.getSession();
        String account = (String)session.getAttribute("user");
        System.out.println(account+":"+id+":"+amount+"type:"+type);
        if (type==0){
            allService.update(account,id,amount,type,price);
            return "output";
        }
        if (type==1){
            allService.update(account,id,amount,type,price);
            return  "input";
        }
        return  "except";
    }
    @GetMapping("/transfer")
    public String transfer(@RequestParam Integer id ,@RequestParam Integer amount,
                            @RequestParam String account,HttpServletRequest request){
        HttpSession session=request.getSession();
        String user = (String)session.getAttribute("user");
        boolean bool=allService.transfer(user,account,id,amount);
        if (!bool)
            return "transfer";
        else
            return "except";
    }
}
