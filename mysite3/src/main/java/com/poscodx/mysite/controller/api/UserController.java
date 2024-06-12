package com.poscodx.mysite.controller.api;

import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("userApiController")
@RequestMapping("/user/api")
public class UserController {

    @Autowired
    private UserService userService;

    // @ResponseBody
    @GetMapping("/checkemail")
    public Object checkEmail(@RequestParam(value="email", required = true, defaultValue="") String email) {
        UserVo vo = userService.getUser(email);

        return Map.of("exist", vo != null);
    }
}
