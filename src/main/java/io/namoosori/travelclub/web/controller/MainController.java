package io.namoosori.travelclub.web.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping("/main") // 조회할 때에는 Get을 쓴다.
    public String welcome(){
        return "Hello World! this comment is from a backEnd.";
    }

    @GetMapping("/login/{oauth2}")
    public String loginGoogle(@PathVariable String oauth2) {
        return "redirect:/oauth2/authorization/" + oauth2;
    }
}
