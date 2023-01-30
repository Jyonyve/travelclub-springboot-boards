package io.namoosori.travelclub.web.util.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.vo.Provider;
import io.namoosori.travelclub.web.aggregate.club.vo.Roles;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.logic.MemberServiceLogic;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GoogleAuthentification {

    private MemberService memberService;

    public GoogleAuthentification() {
        this.memberService = MemberServiceLogic.getMemberServiceLogic();
    }

    public String insertUserInDB(Map<String, String> tokens) throws IOException {

        try {
            DecodedJWT decodedJWT = JWT.decode(tokens.get("id_token"));
            String payload = new String(Base64Utils.decode(decodedJWT.getPayload().getBytes(StandardCharsets.UTF_8)));

            Gson gson = new Gson();
            Map<String, Object> map = gson.fromJson(payload, Map.class);
            for(Map.Entry<String, Object> entry : map.entrySet()){
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }

            String id_token = tokens.get("id_token");
            System.out.println("id_token : " + id_token);
            MemberCdo memberCdo = new MemberCdo(String.valueOf(map.get("name")), String.valueOf(map.get("email")),
                    "010-0000-0000", Provider.GOOGLE, id_token);
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
            nameValueList.addNameValue("id_token", memberCdo.getId_token());
            if(memberCdo.getEmail().equals("nthpopuptown@gmail.com")){
                nameValueList.addNameValue("roles", Roles.ADMIN);
            } else{
                nameValueList.addNameValue("roles", Roles.MEMBER);
            }
            memberService.modifyMember(communityMember.getId(), nameValueList);
            return communityMember.getId();
        }
    }

    public String getUserRoles(String id){
        return memberService.findMemberById(id).getRoles().name();
    }

    public Map<String, String > responseParser(ResponseEntity<String> response) throws IOException {
        Map<String, String> tokensMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response.getBody());
        tokensMap.put("access_token", node.path("access_token").asText());
        tokensMap.put("refresh_token", node.path("refresh_token").asText());
        tokensMap.put("scope", node.path("scope").asText());
        tokensMap.put("id_token", node.path("id_token").asText());
        tokensMap.put("token_type", node.path("token_type").asText());

        return tokensMap;
    }
}
