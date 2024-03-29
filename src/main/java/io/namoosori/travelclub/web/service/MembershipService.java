package io.namoosori.travelclub.web.service;

import io.namoosori.travelclub.web.aggregate.club.Membership;
import io.namoosori.travelclub.web.service.sdo.MembershipCdo;
import io.namoosori.travelclub.web.shared.NameValueList;

import java.util.List;

public interface MembershipService {
    //
    Membership registerMembership(MembershipCdo membershipCdo);
    Membership findMembership(String membershipId);
    Membership findMembershipByClubIdAndMemberId(String clubId, String memberId);
    Membership findMembershipByClubIdAndMemberEmail(String clubId, String memberEmail);
    List<Membership> findAllMembershipsOfClub(String clubId);
    List<Membership> findAllMembershipsOfMember(String memberId);
    void modifyMembership(String membershipId, NameValueList nameValueList);
    void removeMembership(String membershipId);
    boolean existChecker(String clubId, String memberId);
    boolean existChecker(String membershipId);
    List<Membership> findByEmail(String email);
    boolean isClubMember (String clubId, String memberEmail);
}
