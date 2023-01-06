package io.namoosori.travelclub.web.controller;


import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model ) {
        // .............
        // 사용자 정보: 위의 @LoginUser 어노테이션으로 대체
         MemberCdo memberCdo = (MemberCdo) httpSession.getAttribute("member");
        if(memberCdo != null) {
            model.addAttribute("name", memberCdo.getName());
            model.addAttribute("email", memberCdo.getPhoneNumber());
        }
        return "index";
    }
    // ..................
}