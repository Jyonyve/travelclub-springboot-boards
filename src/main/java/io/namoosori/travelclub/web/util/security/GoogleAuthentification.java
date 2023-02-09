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
import io.namoosori.travelclub.web.store.jpastore.jpo.MemberJpo;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class GoogleAuthentification {

    private MemberService memberService;
    private static GoogleAuthentification googleAuthentification;

    private GoogleAuthentification() {
        this.memberService = MemberServiceLogic.getMemberServiceLogic();
    }

    public static GoogleAuthentification getGoogleAuthentification(){
        if (googleAuthentification == null){
            googleAuthentification = new GoogleAuthentification();
        }
        return googleAuthentification;
    }


    public ResponseEntity<String> firstCredential(String code){

        RestTemplate restTemplate = new RestTemplate();

        String credentials = "642225847404-je5i44c2t5d6jskll3sk82nqh233ejlk.apps.googleusercontent.com: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";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + encodedCredentials);

        HttpEntity<String> request = new HttpEntity<>(headers);
        String access_token_url = "https://oauth2.googleapis.com/token";
        access_token_url += "?code=" + code;
        access_token_url += "&grant_type=authorization_code";
        access_token_url += "&redirect_uri=http://localhost:3000/login/oauth2/code/google";

        return restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);
    }

    public Map<String, Object> JWTTokenDecoder(String idToken){
        DecodedJWT decodedJWT = JWT.decode(idToken);
        String payload = new String(Base64Utils.decode(decodedJWT.getPayload().getBytes(StandardCharsets.UTF_8)));

        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(payload, Map.class);
//            for(Map.Entry<String, Object> entry : map.entrySet()){
//                System.out.println(entry.getKey() + "=" + entry.getValue());
//            }
        return map;
    }

    public String insertUserInDB(Map<String, String> tokens) throws IOException {
        try {
            String id_token = tokens.get("id_token");
            Map<String, Object> map = JWTTokenDecoder(id_token);
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
            nameValueList.addNameValue("idToken", memberCdo.getIdToken());
            if(memberCdo.getEmail().equals("nthpopuptown@gmail.com")){
                nameValueList.addNameValue("roles", Roles.ADMIN.getKey());
            } else{
                nameValueList.addNameValue("roles", Roles.MEMBER.getKey());
            }
            memberService.modifyMember(communityMember.getId(), nameValueList);
            return communityMember.getId();
        }
    }

    public List<String> getUserRoles(String id){
        return new MemberJpo(memberService.findMemberById(id)).getAuthorities().stream()
                .map(role -> role.getAuthority()).collect(Collectors.toList());
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

    public boolean adminChecker (String email) {
        return memberService.findAllByRoles(Roles.ADMIN).stream().anyMatch(admin -> admin.getEmail().equals(email));
    }

}
