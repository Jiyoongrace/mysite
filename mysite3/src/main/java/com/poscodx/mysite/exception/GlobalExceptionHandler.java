package com.poscodx.mysite.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handler(Exception e, Model model) {
        // 1. 로깅(logging)
//        String error ="";
//        e.printStackTrace();
//        System.out.println(e);

        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        System.out.println(errors.toString());

        // 2. 사과(종료)
        model.addAttribute("error", errors.toString());
        return "errors/exception";
    }
}