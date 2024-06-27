package com.poscodx.mysite.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.web.servlet.error.ErrorController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class WhitelabelErrorController implements ErrorController {

    // view resolver를 통해 404 page를 보여주기
    @RequestMapping("/404")
    public String _404() {
        return "views/errors/404";
    }

    @RequestMapping("/500")
    public String _500() {
        return "views/errors/500";
    }

    @RequestMapping("")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null) {
            int statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "views/errors/404";
            } else if(statusCode == HttpStatus.BAD_REQUEST.value()) {
                return "views/errors/400";
            } else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return "views/errors/403";
            } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "views/errors/500";
            }
        }
        return "views/errors/unknown";
    }
}
