package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.Membership;
import io.namoosori.travelclub.web.aggregate.club.TravelClub;
import io.namoosori.travelclub.web.service.ClubService;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.MembershipService;
import io.namoosori.travelclub.web.service.sdo.MembershipCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.ClubStore;
import io.namoosori.travelclub.web.store.MemberStore;
import io.namoosori.travelclub.web.store.MembershipStore;
import io.namoosori.travelclub.web.util.exception.MembershipDuplicationException;
import io.namoosori.travelclub.web.util.exception.NoSuchClubException;
import io.namoosori.travelclub.web.util.exception.NoSuchMemberException;
import io.namoosori.travelclub.web.util.exception.NoSuchMembershipException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipServiceLogic implements MembershipService {
    //
    private static MembershipService membershipService;
    private static MembershipStore membershipStore;
    private MemberService memberService;
    private ClubService clubService;
    private MembershipServiceLogic(MembershipStore membershipStore){
        this.membershipStore = membershipStore;
        this.memberService = MemberServiceLogic.getMemberServiceLogic();
        this.clubService = ClubServiceLogic.getClubServiceLogic();
    };

    public static MembershipService getMembershipServiceLogic() {
        if (membershipService==null){
            membershipService = new MembershipServiceLogic(membershipStore);
        }
        return membershipService;
    };

    @Override
    public Membership registerMembership(MembershipCdo membershipCdo) {
        String clubId = membershipCdo.getClubId();
        String memberId = membershipCdo.getMemberId();

        if (clubService.existChecker(clubId)
            && memberService.existChecker(memberId)
            && !existChecker(clubId, memberId))
        {
            Membership membership = new Membership(membershipCdo);
            membershipStore.create(membership);
            return membership;
        } else {
            throw new MembershipDuplicationException("already exists member.");
        }
    }

    @Override
    public Membership findMembership(String membershipId) {
        //
        return membershipStore.retrieve(membershipId);
    }

    @Override
    public Membership findMembershipByClubIdAndMemberId(String clubId, String memberId) {
        //
        return membershipStore.retrieveByClubIdAndMemberId(clubId, memberId);
    }

    @Override
    public Membership findMembershipByClubIdAndMemberEmail(String clubId, String memberEmail) {
        //
        String memberId = memberService.findMemberByEmail(memberEmail).getId();
        return membershipStore.retrieveByClubIdAndMemberId(clubId, memberId);

    }

    @Override
    public List<Membership> findAllMembershipsOfClub(String clubId) {
        //
        return membershipStore.retrieveByClubId(clubId);
    }

    @Override
    public List<Membership> findAllMembershipsOfMember(String memberId) {
        //
        return membershipStore.retrieveByMemberId(memberId);
    }

    @Override
    public void modifyMembership(String membershipId, NameValueList nameValueList) {
        //
        Membership membership = membershipStore.retrieve(membershipId);

        if (membership == null) {
            throw new NoSuchMembershipException("No such membership");
        }

        membership.modifyValues(nameValueList);

        membershipStore.update(membership);
    }

    @Override
    public void removeMembership(String membershipId) {
        //
        membershipStore.delete(membershipId);
    }

    @Override
    public boolean existChecker(String clubId, String memberId) {
        return membershipStore.exists(clubId, memberId);
    }

    @Override
    public boolean existChecker(String membershipId) {
        return membershipStore.exists(membershipId);
    }

    @Override
    public List<Membership> findByEmail(String email) {
        return membershipStore.retrieveByEmail(email);
    }

    @Override
    public boolean isClubMember(String clubId, String memberEmail) {
        return findMembershipByClubIdAndMemberEmail(clubId, memberEmail) != null;
    }
}
