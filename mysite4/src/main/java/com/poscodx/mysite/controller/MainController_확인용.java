package com.poscodx.mysite.controller;//package com.poscodx.mysite.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Enumeration;
//
//@Controller
//public class MainController_확인용 {
//    @Autowired
//    private ApplicationContext appContext;
//
//    @RequestMapping({"/", "/main"})
//    public String index(HttpServletRequest request) {
//        ServletContext sc = request.getServletContext();
//        Enumeration<String> names = (Enumeration<String>) sc.getAttributeNames();
//
//        while(names.hasMoreElements()) {
//            String name = names.nextElement();
//            System.out.println(name);
//        }
//        ApplicationContext ac1 = (ApplicationContext) sc.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
//        ApplicationContext ac2 = (ApplicationContext) sc.getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher-servlet");
//
//        System.out.println(ac1);
//        System.out.println(ac2);
//
//        return "main/index";
//    }
//}
