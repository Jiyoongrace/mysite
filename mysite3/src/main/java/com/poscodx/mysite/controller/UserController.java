package com.poscodx.mysite.controller;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String join() {
        return "user/join";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join(UserVo vo) {
        userService.join(vo);
        System.out.println(vo);
        return "redirect:/user/joinsuccess";
    }

    @RequestMapping(value = "/joinsuccess", method = RequestMethod.GET)
    public String joinsuccess() {
        return "user/joinsuccess";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "user/login";
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(HttpSession session, UserVo vo, Model model) {
//        UserVo authUser = userService.getUser(vo.getEmail(), vo.getPassword());
//
//        if (authUser == null) {
//            model.addAttribute("email", vo.getEmail());
//            model.addAttribute("result", "fail");
//
//            return "user/login";
//        }
//
//        session.setAttribute("authUser", authUser);
//
//        return "redirect:/";
//    }

//    @RequestMapping("/logout")
//    public String logout(HttpSession session) {
//        session.removeAttribute("authUser");
//        session.invalidate(); // Jsession ID 새로 만듦
//
//        return "redirect:/";
//    }

    @Auth
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(@AuthUser UserVo authUser, Model model) {
        UserVo vo = userService.getUser(authUser.getNo());
        model.addAttribute("userVo", vo);

        return "user/update";
    }

    @Auth
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@AuthUser UserVo authUser, UserVo vo) {
        vo.setNo(authUser.getNo());
        userService.update(vo);

        authUser.setName(vo.getName());
        return "redirect:/user/update";
    }
}
