package io.namoosori.travelclub.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.Membership;
import io.namoosori.travelclub.web.aggregate.club.vo.RoleInClub;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.MembershipService;
import io.namoosori.travelclub.web.service.logic.MemberServiceLogic;
import io.namoosori.travelclub.web.service.logic.MembershipServiceLogic;
import io.namoosori.travelclub.web.service.sdo.MembershipCdo;
import io.namoosori.travelclub.web.util.security.GoogleAuthentification;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/membership")
public class MembershipController {
    private MembershipService membershipService;
    private MemberService memberService;
    private GoogleAuthentification googleAuthentification;

    public MembershipController() {
        this.membershipService = MembershipServiceLogic.getMembershipServiceLogic();
        this.googleAuthentification = GoogleAuthentification.getGoogleAuthentification();
        this.memberService = MemberServiceLogic.getMemberServiceLogic();
    }

    @PostMapping("/{clubId}")
    public Membership registerMembership(@PathVariable("clubId") String clubId, @RequestBody MembershipCdo membershipCdo,
                                     @RequestHeader("Authorization") String bearerIdToken){
        String idToken = bearerIdToken.substring(7);
        Map<String, Object> payload = googleAuthentification.JWTTokenDecoder(idToken);
        String memberEmail = String.valueOf(payload.get("email"));

        CommunityMember member = memberService.findMemberByEmail(memberEmail);

        membershipCdo.setMemberId(member.getId());
        membershipCdo.setEmail(memberEmail);
        membershipCdo.setPassword(member.getPassword());

        return membershipService.registerMembership(membershipCdo);
    }

    //requestParam
    @GetMapping
    public String findClubIdAndRoleInClubByEmail(@RequestHeader("Authorization") String bearerIdToken) throws JsonProcessingException {
        //
        String idToken = bearerIdToken.substring(7);
        Map<String, Object> payload = googleAuthentification.JWTTokenDecoder(idToken);
        String memberEmail = String.valueOf(payload.get("email"));

        List<Membership> membershipList= membershipService.findByEmail(memberEmail);

        Map<String, RoleInClub> membershipIdAndRoleInClub = new HashMap<>();
        membershipList.stream().forEach( membership ->
            membershipIdAndRoleInClub.put(membership.getId(), membership.getRoleInClub())
        );
        membershipIdAndRoleInClub.put("sampleClubId", RoleInClub.President);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(membershipIdAndRoleInClub);
    }

//    //path/path
//    @GetMapping("/{clubId}/{memberId}")
//    public Membership findMembershipByClubIdAndMemberId(@PathVariable String clubId, @PathVariable String memberId){
//        return membershipService.findMembershipByClubIdAndMemberId(clubId, memberId);
//    }
//
//    //path/param
//    @GetMapping("/{clubId}")
//    public Membership findMembershipByClubIdAndMemberEmail(@PathVariable String clubId, @RequestParam String memberEmail){
//        return membershipService.findMembershipByClubIdAndMemberEmail(clubId, memberEmail);
//    }
//
//
//    //전체 조회
//    @GetMapping("/all/{clubId}")
//    public List<Membership> findAllMembershipsOfClub(@PathVariable String clubId){
//        return membershipService.findAllMembershipsOfClub(clubId);
//    }
//
//    @GetMapping("/all/")
//    public List<Membership> findAllMembershipsOfMember(@RequestParam String memberId){
//        return membershipService.findAllMembershipsOfMember(memberId);
//    }
//
//
//
//    @PutMapping("/{membershipId}")
//    public void modifyMembership(@PathVariable String membershipId, @RequestBody NameValueList nameValueList){
//        membershipService.modifyMembership(membershipId, nameValueList);
//    }
//
//    @DeleteMapping("/{membershipId}")
//    public void removeMembership(@PathVariable String membershipId){
//        membershipService.removeMembership(membershipId);
//    }
//


}
