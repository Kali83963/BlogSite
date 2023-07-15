package com.kashif.BlogSite.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home(){

        return "home";
    }

    @GetMapping("/addnewpost")
    public String addPost(){

        return "new_post";
    }

    @GetMapping("/getpost")
    public String getpost(){

        return "single_post";
    }

    @GetMapping("/editpost")
    public String editpost(){
        return "edit_post";
    }

    @GetMapping("/login")
    public String login(){

        return "login";
    }

    @GetMapping("/register")
    public String register(){

        return "registration";
    }

}
