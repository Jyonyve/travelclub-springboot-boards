package io.namoosori.travelclub.web.service.sdo;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.vo.Provider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MemberCdo implements Serializable {
    //
    private String email;
    private String name;
    //private String nickName;
    private String phoneNumber;
    private Provider provider;
    private String idToken;
    private String password;

    public MemberCdo(CommunityMember member) {
        BeanUtils.copyProperties(member, this);
    }
//    private Address addresses;

    public MemberCdo(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = String.valueOf(Math.round(Math.random()*1000000));
    }

    public MemberCdo(String name, String email, String phoneNumber, Provider provider, String idToken) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.provider = provider;
        this.idToken = idToken;
        this.password = String.valueOf(Math.round(Math.random()*1000000));
    }
}
