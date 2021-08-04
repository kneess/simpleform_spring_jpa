package com.perscholas.controllers;

import com.perscholas.models.User;
import com.perscholas.services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/simpleforms")
public class UserController {

    @Autowired
    UserServices userServices;

    @GetMapping({"/","/welcome"})
    public String welcome(Model model)
    {
        log.warn("Get req welcome page");
        Date date = new Date();
        model.addAttribute("date", date);
        return "welcome";
    }


    @GetMapping("/login")
    public String loginPage()
    {
      log.warn("Get request login page");

      return "login-form";
    }

    @ModelAttribute("theuser")
    public User newUser()
    {
        return new User();
    }

    @PostMapping("/login/process")
    public String processLogin(@ModelAttribute("theuser") User user, Model model)
    {
         User foundUser = userServices.getUserByEmail(user.getEmail());
         log.warn(foundUser.toString());

         //if user is found
         if(foundUser != null)
         {
             String fullname = foundUser.getFirstName() + " "+ foundUser.getLastName();
             model.addAttribute("username", fullname);
             //check if password matches
             return foundUser.getPassword().equals(user.getPassword()) ? "login-success" : "login-form";
         }

         return "login-form";
    }

}
