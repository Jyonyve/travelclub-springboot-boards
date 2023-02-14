package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.club.Membership;
import io.namoosori.travelclub.web.store.MembershipStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.MembershipJpo;
import io.namoosori.travelclub.web.store.jpastore.repository.MembershipRepository;
import io.namoosori.travelclub.web.util.exception.NoSuchMembershipException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MembershipJpaStore implements MembershipStore {

    private MembershipRepository membershipRepository;

    public MembershipJpaStore(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Membership create(Membership membership) {
        membershipRepository.save(new MembershipJpo(membership));

        return membership;
    }

    @Override
    public Membership retrieve(String membershipId) {
        Optional<MembershipJpo> membershipJpo = membershipRepository.findById(membershipId);
        return membershipJpo.get().toDomain();
    }

    @Override
    public Membership retrieveByClubIdAndMemberId(String clubId, String memberId) {
        Optional<MembershipJpo> membershipJpo = Optional.ofNullable(membershipRepository.findByTravelClubJpo_IdAndMemberJpo_Id(clubId, memberId));

        return membershipJpo.map(MembershipJpo::toDomain).orElse(null);
    }

    @Override
    public List<Membership> retrieveByClubId(String clubId) {
        List<MembershipJpo> membershipJpos = membershipRepository.findByTravelClubJpo_Id(clubId);
        return membershipJpos.stream().map(MembershipJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Membership> retrieveByMemberId(String memberId) {
        List<MembershipJpo> membershipJpos = membershipRepository.findByMemberJpo_Id(memberId);
        return membershipJpos.stream().map(MembershipJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Membership> retrieveByEmail(String email) {
        List<MembershipJpo> membershipJpo = membershipRepository.findByEmail(email);
        return membershipJpo.stream().map(MembershipJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public void update(Membership membership) {
        if(!membershipRepository.existsById(membership.getId())){
            throw new NoSuchMembershipException("no membership in here");
        }
        membershipRepository.save(new MembershipJpo(membership));
    }

    @Override
    public void delete(String membershipId) {
        if(!membershipRepository.existsById(membershipId)){
            throw new NoSuchMembershipException("No such Membership in here");
        }
        membershipRepository.deleteById(membershipId);
    }

    @Override
    public boolean exists(String membershipId) {
        return membershipRepository.existsById(membershipId);
    }

    @Override
    public boolean exists(String clubId, String memberId) {
        return membershipRepository.findByTravelClubJpo_IdAndMemberJpo_Id(clubId,memberId) !=null;
    }
}
