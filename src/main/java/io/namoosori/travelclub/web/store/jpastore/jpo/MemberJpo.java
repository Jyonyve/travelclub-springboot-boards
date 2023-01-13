package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.vo.Role;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "Community_Member")
@NoArgsConstructor
public class MemberJpo implements UserDetails {

    @Id
    @JoinColumn(name = "memberId")
    private String id;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String birthday;
    private String password;
//    @Enumerated(EnumType.STRING)
//    private Role role;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> roles = new ArrayList<>();

    private String provider;
//    private String providerId;

    public MemberJpo(CommunityMember member){
        BeanUtils.copyProperties(member,this);
    }

    public MemberJpo(MemberCdo memberCdo){
        BeanUtils.copyProperties(memberCdo,this);
    }


    @OneToOne(mappedBy = "memberJpo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AddressJpo addressJpo;
    @OneToMany(mappedBy = "memberJpo")
    private List<MembershipJpo> membershipJpos = new ArrayList<>();

    public void setAddressJpo(AddressJpo addressJpo){
        this.addressJpo = addressJpo;
        addressJpo.setMemberJpo(this);
    }
    public CommunityMember toDomain(){
        CommunityMember member = new CommunityMember(this.email, this.name, this.phoneNumber, this.password );
        member.setBirthDay(this.birthday);
        member.setNickName(nickname);
        member.setPassword(password);
        member.setRole(Role.MEMBER);
        //member.setAddresses(splitAddress(addresses));

        return member;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
