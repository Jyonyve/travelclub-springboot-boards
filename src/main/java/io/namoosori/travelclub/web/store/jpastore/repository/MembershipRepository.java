package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.store.jpastore.jpo.MembershipJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<MembershipJpo, String> {

    List<MembershipJpo> findAllByName(String name);
    MembershipJpo findByTravelClubJpo_IdAndMemberJpo_Id(String clubId, String memberId);
    List<MembershipJpo> findByTravelClubJpo_Id(String clubId);
    List<MembershipJpo> findByMemberJpo_Id(String memberId);
    Optional<MembershipJpo> findByEmail(String email);
}
