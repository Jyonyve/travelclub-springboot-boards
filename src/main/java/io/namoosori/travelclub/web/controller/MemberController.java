package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.jpastore.jpo.MemberJpo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public String register(@RequestBody MemberCdo memberCdo){
        return memberService.register(memberCdo);
    }

    @GetMapping
    public List<CommunityMember> findAll(){
        return memberService.findAll();
    }

    @GetMapping("/{memberId}")
    public CommunityMember findMemberById(@PathVariable String memberId){
        return memberService.findMemberById(memberId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')") //관리자만 볼 수 있음.
    @GetMapping("/?email={memberEmail}") //url을 작성할 때 ?memberEmail=(email)로 넣어서 보내줘야 함.
    public CommunityMember findMemberByEmail(@RequestParam String memberEmail){
        return memberService.findMemberByEmail(memberEmail);
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')") //멤버만 볼 수 있음
    @GetMapping("/?name={name}") //url을 작성할 때 ?name=(김은진)로 넣어서 보내줘야 함.
    public List<CommunityMember> findMembersByName(@PathVariable String name){
        return memberService.findMembersByName(name);
    }

//    @PutMapping("/{memberId}")
//    public void modifyMember(@PathVariable String memberId, @RequestBody NameValueList member){
//        memberService.modifyMember(memberId, member);
//    }

    @PutMapping("/{id}")
    public void modifyReact(@PathVariable String id, @RequestBody MemberJpo memberJpo){
        NameValueList nameValueList = new NameValueList();
        nameValueList.addNameValue("email", memberJpo.getEmail());
        nameValueList.addNameValue("name", memberJpo.getName());
        nameValueList.addNameValue("nickName", memberJpo.getNickname());
        nameValueList.addNameValue("phoneNumber", memberJpo.getPhoneNumber());
        nameValueList.addNameValue("birthday", memberJpo.getBirthday());
        nameValueList.addNameValue("id", memberJpo.getId());
    }


    @DeleteMapping("/{id}")
    public void removeMember(@PathVariable String id){
        memberService.removeMember(id);
    }

}
