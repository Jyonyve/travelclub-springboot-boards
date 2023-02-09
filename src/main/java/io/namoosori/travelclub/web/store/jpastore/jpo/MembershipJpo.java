package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.Membership;
import io.namoosori.travelclub.web.aggregate.club.vo.RoleInClub;
import io.namoosori.travelclub.web.aggregate.club.vo.Roles;
import io.namoosori.travelclub.web.service.logic.BoardServiceLogic;
import io.namoosori.travelclub.web.service.logic.ClubServiceLogic;
import io.namoosori.travelclub.web.service.logic.MemberServiceLogic;
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
    private String joinDate;
    private String nickname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles roles;
    @Enumerated(EnumType.STRING)
    private RoleInClub roleInClub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubId")
    private TravelClubJpo travelClubJpo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberJpo memberJpo;

    public MembershipJpo(Membership membership) {
        BeanUtils.copyProperties(membership, this);
        this.memberJpo = new MemberJpo(MemberServiceLogic.getMemberServiceLogic().findMemberById(membership.getMemberId()));
        this.travelClubJpo = new TravelClubJpo(ClubServiceLogic.getClubServiceLogic().findClubById(membership.getClubId()));
    }

    public Membership toDomain(){
        Membership membership = new Membership(this);
        membership.setClubId(this.travelClubJpo.getId());
        membership.setMemberId(this.memberJpo.getId());
        return membership;
    }
}
