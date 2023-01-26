package io.namoosori.travelclub.web.util.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.logic.MemberServiceLogic;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.jpastore.MemberJpaStore;
import io.namoosori.travelclub.web.store.jpastore.repository.MemberRepository;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Map;

public class GoogleAuthentification {

    private MemberService memberService;

    public GoogleAuthentification() {
        this.memberService = MemberServiceLogic.getMemberServiceLogic();
    }

    public String authenticateUser(HttpServletRequest request, HttpServletResponse response) throws GeneralSecurityException, IOException {

        String token = request.getHeader("Authorization");
        System.out.println(token);
        token = token.substring(7);//Bearer 재거한 토큰 받음
        System.out.println(token);

        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String payload = new String(Base64Utils.decode(decodedJWT.getPayload().getBytes(StandardCharsets.UTF_8)));

            Gson gson = new Gson();
            Map<String, Object> map = gson.fromJson(payload, Map.class);
            for(Map.Entry<String, Object> entry : map.entrySet()){
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }

            MemberCdo memberCdo = new MemberCdo(String.valueOf(map.get("name")), String.valueOf(map.get("email")), "010-0000-0000");
            return saveOrUpdate(memberCdo);

        } catch (JWTDecodeException decodeEx) {
            System.out.println("Decode Error");
            decodeEx.printStackTrace();
            throw new IOException("Something wrong happens within saveOrUpdate");
        }
    }

    public String saveOrUpdate(MemberCdo memberCdo){
        CommunityMember communityMember = memberService.findMemberByEmail(memberCdo.getEmail());
        if ( communityMember== null){
            return memberService.register(memberCdo);
        }else {
            NameValueList nameValueList = new NameValueList();
            nameValueList.addNameValue("name", memberCdo.getName());
            memberService.modifyMember(communityMember.getId(), nameValueList);
            return communityMember.getId();
        }
    }
}
