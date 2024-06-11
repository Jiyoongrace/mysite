package com.poscodx.mysite.controller;

import com.poscodx.mysite.service.SiteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    private SiteService siteService;

    public MainController(SiteService siteService) {
        this.siteService = siteService;
    }

    @RequestMapping({"/", "/main"})
    public String index(HttpServletRequest request) {
        return "main/index";
    }
}
