package io.namoosori.travelclub.web.util.security;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth2 를 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String name;
    private String email;
    private String phoneNumber;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String phoneNumber) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static OAuthAttributes of(String regiId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .phoneNumber(String.valueOf(attributes.get("phoneNumber")))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build(); //이 값들을 생성자에 넣어서 만들어 주세요.
    }
}
