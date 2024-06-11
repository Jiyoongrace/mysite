package com.poscodx.mysite.controller;

import com.poscodx.mysite.security.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Auth(role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/board")
    public String board() {
        return "";
    }

    @RequestMapping("/user")
    public String board() {
        return "";
    }
}
