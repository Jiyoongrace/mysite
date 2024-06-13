package com.poscodx.mysite.controller;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public String join(@ModelAttribute UserVo vo) {
        return "user/join";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
        if(result.hasErrors()) {
            // model.addAttribute("userVo", vo); @ModelAttribute 와 동일함
//            List<ObjectError> list = result.getAllErrors();
//            for(ObjectError error : list) {
//                System.out.println(error);
//            }

            Map<String, Object> map = result.getModel();
//            Set<String> s = map.keySet();
//            for(String key : s) {
//                model.addAttribute(key, map.get(key));
//            }
            // model.addAllAttributes(map); // 위 3줄과 동일하다.

            model.addAllAttributes(map);

            return "user/join";
        }

        // userService.join(vo);
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

    @Auth // (value = "hello") : value만 생략 가능, value가 아닌 다른 값의 이름은 적어줘야 함
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
