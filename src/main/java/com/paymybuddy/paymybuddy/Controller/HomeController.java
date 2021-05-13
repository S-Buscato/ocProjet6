package com.paymybuddy.paymybuddy.Controller;

import com.paymybuddy.paymybuddy.Service.IServices.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController{

    @Autowired
    UsersService usersService;

    @RequestMapping("/")
    public String home(Model model)
    {
        return "home";
    }

    @RequestMapping("/admin/home")
    public String adminHome(Model model)
    {
        model.addAttribute("users", usersService.findall());
        return "/users/list";
    }

}
