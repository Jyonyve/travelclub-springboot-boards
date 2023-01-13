package io.namoosori.travelclub.web.util.security;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.store.jpastore.jpo.MemberJpo;
import lombok.Data;

import java.util.List;
@Data
public class JwtResponse {

    private String jwtToken;
    private CommunityMember member;
    private List<String> roles;

    public JwtResponse () {

    }

    public JwtResponse(String token, CommunityMember member, List<String> roles) {
        this.jwtToken = token;
        this.member = member;
        this.roles = roles;
    }


    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }



}
