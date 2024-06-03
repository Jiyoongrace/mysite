package com.poscodx.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @Autowired
    private ApplicationContext appContext;

    @RequestMapping({"/", "/main"})
    public String index(HttpServletRequest request) {
        return "main/index";
    }
}
