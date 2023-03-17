package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.logic.MemberServiceLogic;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.service.jpo.MemberJpo;
import io.namoosori.travelclub.web.util.security.GoogleAuthentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost:3000", "http://localhost:8080", "https://localhost:3000", "https://localhost:8080"},
        methods = {RequestMethod.HEAD, RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = {"Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization", "Access-Control-Allow-Credentials", "X-AUTH-TOKEN"}
)
@RestController
@RequestMapping(value = "/member")
public class MemberController {

    private MemberService memberService;
    private GoogleAuthentication googleAuthentication;
    public MemberController() {
        this.memberService = MemberServiceLogic.getMemberServiceLogic();
        this.googleAuthentication = GoogleAuthentication.getGoogleAuthentication();
    }
    @PostMapping
    public String register(@RequestBody MemberCdo memberCdo){
        return memberService.register(memberCdo);
    }

    @GetMapping
    public List<CommunityMember> findAll(@RequestHeader("Authorization") String bearerIdToken){
        String idTokenFront = bearerIdToken.substring(7);
        Map<String, Object> payload = googleAuthentication.JWTTokenDecoder(idTokenFront);

        if(googleAuthentication.adminChecker(String.valueOf(payload.get("email")))){
            System.out.println("Admin Signed in. Request Member List.");
            return memberService.findAll();
        }
        return null;
    }

//    @GetMapping("/{memberId}")
//    public CommunityMember findMemberById(@PathVariable String memberId){
//        return memberService.findMemberById(memberId);
//    }

//    @GetMapping("/?email={memberEmail}") //url을 작성할 때 ?memberEmail=(email)로 넣어서 보내줘야 함.
//    public CommunityMember findMemberByEmail(@RequestParam String memberEmail){
//        return memberService.findMemberByEmail(memberEmail);
//    }

//    @PreAuthorize("hasRole('ROLE_MEMBER', 'ROLE_ADMIN')")
//    @GetMapping("/?name={name}") //url을 작성할 때 ?name=(김은진)로 넣어서 보내줘야 함.
//    public List<CommunityMember> findMembersByName(@PathVariable String name){
//        return memberService.findMembersByName(name);
//    }

//    @PutMapping("/{memberId}")
//    public void modifyMember(@PathVariable String memberId, @RequestBody NameValueList member){
//        memberService.modifyMember(memberId, member);
//    }

    @PutMapping("/{id}")
    public void modifyReact(@PathVariable String id, @RequestBody MemberJpo memberJpo){
        NameValueList nameValueList = new NameValueList();
        nameValueList.addNameValue("email", memberJpo.getEmail());
        nameValueList.addNameValue("name", memberJpo.getName());
        nameValueList.addNameValue("phoneNumber", memberJpo.getPhoneNumber());
        memberService.modifyMember(id, nameValueList);
    }


    @DeleteMapping("/{id}")
    public void removeMember(@PathVariable String id){
        memberService.removeMember(id);
    }

}
