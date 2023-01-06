package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final HttpSession httpSession;

    @PostMapping
    public String index(Model model){
        MemberCdo memberCdo = (MemberCdo) httpSession.getAttribute("member");

        if(memberCdo != null){
            model.addAttribute("name", memberCdo.getName());
        }
        return "index";
    }
}