package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.Membership;
import io.namoosori.travelclub.web.aggregate.club.vo.Role;
import io.namoosori.travelclub.web.aggregate.club.vo.RoleInClub;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Membership")
@NoArgsConstructor
public class MembershipJpo {
    @Id
    private String id;
//    private String clubId;
//    private String memberId;
    private String joinDate;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private RoleInClub roleInClub;



    @ManyToOne
    @JoinColumn(name = "clubId")
    private TravelClubJpo travelClubJpo;
    @ManyToOne
    @JoinColumn(name = "memberId")
    private MemberJpo memberJpo;

    public MembershipJpo(Membership membership) {
        BeanUtils.copyProperties(membership, this);
    }

    public Membership toDomain(){
        Membership membership = new Membership(travelClubJpo.getId(), memberJpo.getId(), name, email, password );
        return membership;
    }
}
