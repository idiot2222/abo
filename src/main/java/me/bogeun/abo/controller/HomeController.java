package me.bogeun.abo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "user/login";
    }
}
