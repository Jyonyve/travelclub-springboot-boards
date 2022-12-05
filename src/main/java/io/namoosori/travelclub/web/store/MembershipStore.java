package io.namoosori.travelclub.web.store;

import io.namoosori.travelclub.web.aggregate.club.Membership;

import java.util.List;
import java.util.Optional;

public interface MembershipStore {
    //
    String create(Membership membership);
    Membership retrieve(String membershipId);
    Membership retrieveByClubIdAndMemberId(String clubId, String memberId);
    List<Membership> retrieveByClubId(String clubId);
    List<Membership> retrieveByMemberId(String memberId);
    Optional<Membership> retrieveByEmail(String email);
    void update(Membership membership);
    void delete(String membershipId);

    boolean exists(String membershipId);
}
