package com.perscholas.controllers;

import com.perscholas.models.User;
import com.perscholas.services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/simpleforms")
public class UserController {

    @Autowired
    UserServices userServices;

    //go to welcome page
    @GetMapping({"/","/welcome"})
    public String welcome(Model model)
    {
        log.warn("Get req welcome page");
        Date date = new Date();
        model.addAttribute("date", date);
        return "welcome";
    }


    // go to login
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

    //process login before going to success page
    @PostMapping("/login/process")
    public String processLogin(@ModelAttribute("theuser") @Valid User user, BindingResult bindingResult, Model model)
    {
        //checking if email or password meet validation requirements
        if(bindingResult.hasErrors())
        {
            log.warn(bindingResult.getAllErrors().toString());
            return "login-form";
        }

        //userservice -> userrepository to get user by email
         User foundUser = userServices.getUserByEmail(user.getEmail());

         //if user is found
         if(foundUser != null)
         {
             log.warn(foundUser.toString());
             String fullname = foundUser.getFirstName() + " "+ foundUser.getLastName();
             model.addAttribute("username", fullname);
             //check if password matches
             return foundUser.getPassword().equals(user.getPassword()) ? "login-success" : "login-form";
         }

         return "login-form";
    }

}
