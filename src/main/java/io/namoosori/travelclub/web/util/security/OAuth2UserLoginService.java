package io.namoosori.travelclub.web.util.security;

import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.store.jpastore.jpo.MemberJpo;
import io.namoosori.travelclub.web.store.jpastore.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
public class OAuth2UserLoginService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private MemberRepository memberRepository;
    private HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService(); //delegate:위임하다
        OAuth2User oAuth2User = delegate.loadUser(oAuth2UserRequest);

        // OAuth2 서비스 id (구글, 카카오, 네이버)
        String regiId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails()
                                        .getUserInfoEndpoint().getUserNameAttributeName();
        // OAuth2UserService 진행
        OAuthAttributes attributes = OAuthAttributes.of(regiId, userNameAttributeName, oAuth2User.getAttributes());

        MemberJpo memberJpo = saveOrUpdate(attributes);
        httpSession.setAttribute("member", new MemberCdo(memberJpo.toDomain()));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(String.valueOf(memberJpo.getRole()))),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());

    }

    private MemberJpo saveOrUpdate(OAuthAttributes attributes){
        MemberJpo memberJpo = memberRepository.findByEmail(attributes.getEmail());
        memberJpo.setName(attributes.getName());
        memberJpo.setPhoneNumber(attributes.getPhoneNumber());
        return memberRepository.save(memberJpo);
    }
}
