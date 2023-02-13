package io.namoosori.travelclub.web.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ExceptionApiController implements ErrorController {

    @GetMapping({"/index","error"})
    public String root(){
        return "index.html";
    }

    @RequestMapping(value = "/error")
    public String handleNoHandleFoundException(HttpServletResponse response, HttpServletRequest request) {
        return "/error";
    }

}