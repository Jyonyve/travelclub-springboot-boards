package io.namoosori.travelclub.web.store;

import io.namoosori.travelclub.web.aggregate.club.Membership;

import java.util.List;

public interface MembershipStore {
    //
    Membership create(Membership membership);
    Membership retrieve(String membershipId);
    Membership retrieveByClubIdAndMemberId(String clubId, String memberId);
    List<Membership> retrieveByClubId(String clubId);
    List<Membership> retrieveByMemberId(String memberId);
    List<Membership> retrieveByEmail(String email);
    void update(Membership membership);
    void delete(String membershipId);
    boolean exists(String membershipId);
    boolean exists(String clubId, String memberId);
}
