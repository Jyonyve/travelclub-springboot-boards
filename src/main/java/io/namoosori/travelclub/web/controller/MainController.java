package io.namoosori.travelclub.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping("/main") // 조회할 때에는 Get을 쓴다.
    public String welcome(){
        return "Hello World! this comment is from a backEnd.";
    }
}
