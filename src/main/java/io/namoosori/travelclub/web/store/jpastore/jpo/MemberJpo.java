package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.vo.Provider;
import io.namoosori.travelclub.web.aggregate.club.vo.Roles;
import io.namoosori.travelclub.web.aggregate.club.vo.RoleInClub;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "Role_in_Club")
    private RoleInClub roleInClub;
    @Enumerated(EnumType.STRING)
    private Roles roles;
    private Provider provider;
    @Column(columnDefinition = "TEXT")
    private String id_token;

    public MemberJpo(CommunityMember member){
        BeanUtils.copyProperties(member,this);
    }

    public MemberJpo(MemberCdo memberCdo){
        BeanUtils.copyProperties(memberCdo,this);
    }

    @OneToOne(mappedBy = "memberJpo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AddressJpo addressJpo;
    @OneToMany(mappedBy = "memberJpo", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<MembershipJpo> membershipJpos = new ArrayList<>();

    public void setAddressJpo(AddressJpo addressJpo){
        this.addressJpo = addressJpo;
        addressJpo.setMemberJpo(this);
    }
    public CommunityMember toDomain(){
        CommunityMember member = new CommunityMember(this.email, this.name, this.phoneNumber);
        member.setBirthDay(birthday);
        member.setNickName(nickname);
        member.setProvider(provider);
        member.setId(id);
        //member.setAddresses(splitAddress(addresses));

        return member;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
