package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.vo.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Community_Member")
@NoArgsConstructor
public class MemberJpo {

    @Id
    @JoinColumn(name = "memberId")
    private String id;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String birthday;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
//    private String provider;
//    private String providerId;


    @OneToOne(mappedBy = "memberJpo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AddressJpo addressJpo;
    @OneToMany(mappedBy = "memberJpo")
    private List<MembershipJpo> membershipJpos = new ArrayList<>();

    public MemberJpo(CommunityMember member) {
        BeanUtils.copyProperties(member, this);
        //member.getAddresses().stream().map(Address::toString).forEach(address -> this.addresses = address);
    }

    public void setAddressJpo(AddressJpo addressJpo){
        this.addressJpo = addressJpo;
        addressJpo.setMemberJpo(this);
    }
    public CommunityMember toDomain(){
        CommunityMember member = new CommunityMember(this.email, this.name, this.phoneNumber, this.id, this.password);
        member.setId(id);
        member.setBirthDay(this.birthday);
        member.setNickName(nickname);
        member.setPassword(password);
        member.setRole(Role.Member);
        //member.setAddresses(splitAddress(addresses));

        return member;
    }

//    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
//    public MemberJpo(String name, String password, String email, String provider, String providerId){
//        this.name = name;
//        this.password = password;
//        this.email = email;
//        this.role = Role.Member;
//        this.provider = provider;
//        this.providerId = providerId;
//    }


}
