package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.Membership;
import io.namoosori.travelclub.web.aggregate.club.vo.RoleInClub;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Membership")
@NoArgsConstructor
public class MembershipJpo {
    @Id
    private String id;
    private String clubId;
    private String memberId;
    private RoleInClub role;
    private String joinDate;
    private String name;

    public MembershipJpo(Membership membership) {
        BeanUtils.copyProperties(membership, this);
    }

    public Membership toDomain(){
        Membership membership = new Membership(clubId, memberId, name);
        membership.setId(id);
        membership.setRole(role);
        membership.setJoinDate(joinDate);

        return membership;
    }
}
