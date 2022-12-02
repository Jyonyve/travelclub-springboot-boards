package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.club.Membership;
import io.namoosori.travelclub.web.service.MembershipService;
import io.namoosori.travelclub.web.service.sdo.MembershipCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membership")
public class MembershipController {
    private MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping
    public String registerMembership(@RequestBody MembershipCdo membershipCdo){
        return membershipService.registerMembership(membershipCdo);
    }

    //requestParam
    @GetMapping
    public Membership findMembership(@RequestParam String membershipId){
        return membershipService.findMembership(membershipId);
    }

    //path/path
    @GetMapping("/{clubId}/{memberId}")
    public Membership findMembershipByClubIdAndMemberId(@PathVariable String clubId, @PathVariable String memberId){
        return membershipService.findMembershipByClubIdAndMemberId(clubId, memberId);
    }

    //path/param
    @GetMapping("/{clubId}")
    public Membership findMembershipByClubIdAndMemberEmail(@PathVariable String clubId, @RequestParam String memberEmail){
        return membershipService.findMembershipByClubIdAndMemberEmail(clubId, memberEmail);
    }


    //전체 조회
    @GetMapping("/all/{clubId}")
    public List<Membership> findAllMembershipsOfClub(@RequestParam String clubId){
        return membershipService.findAllMembershipsOfClub(clubId);
    }

    @GetMapping("/all/{memberId}")
    public List<Membership> findAllMembershipsOfMember(@RequestParam String memberId){
        return membershipService.findAllMembershipsOfMember(memberId);
    }



    @PutMapping("/{membershipId}")
    public void modifyMembership(@PathVariable String membershipId, @RequestBody NameValueList nameValueList){
        membershipService.modifyMembership(membershipId, nameValueList);
    }

    @DeleteMapping("/{membershipId}")
    public void removeiMembership(@PathVariable String membershipId){
        membershipService.removeMembership(membershipId);
    }



}
