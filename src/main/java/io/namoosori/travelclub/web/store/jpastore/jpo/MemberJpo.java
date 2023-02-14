package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.vo.Provider;
import io.namoosori.travelclub.web.aggregate.club.vo.Roles;
import io.namoosori.travelclub.web.service.logic.MemberServiceLogic;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.util.exception.NoSuchMemberException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Community_Member")
@NoArgsConstructor
public class MemberJpo implements UserDetails {

    @Transient
    private CommunityMember member;
    @Id
    @JoinColumn(name = "memberId")
    private String id;
    private String email;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String birthday;
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles roles;
    private Provider provider;
    @Column(columnDefinition = "TEXT")
    private String idToken;

    public MemberJpo(CommunityMember member){
        BeanUtils.copyProperties(member,this);
    }

    public MemberJpo(MemberCdo memberCdo){
        BeanUtils.copyProperties(memberCdo,this);
    }

    @OneToOne(mappedBy = "memberJpo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AddressJpo addressJpo;
    @OneToMany(mappedBy = "memberJpo", cascade = CascadeType.ALL)
    private List<MembershipJpo> membershipJpos = new ArrayList<>();

    public void setAddressJpo(AddressJpo addressJpo){
        this.addressJpo = addressJpo;
        addressJpo.setMemberJpo(this);
    }
    public CommunityMember toDomain(){
        CommunityMember member = new CommunityMember(this);
        return member;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        member = MemberServiceLogic.getMemberServiceLogic().findMemberById(id);
        if(member == null){
            throw new NoSuchMemberException("No such member with id: " + id);
        }
        //membershipJpo 에서 설정할 것 : String clubName = ClubServiceLogic.getClubServiceLogic().findClubsByName();
        authorities.add(new SimpleGrantedAuthority(member.getRoles().getKey()));
        return authorities;
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
