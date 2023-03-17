package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.service.jpo.MembershipJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<MembershipJpo, String> {

    MembershipJpo findByTravelClubJpo_IdAndMemberJpo_Id(String clubId, String memberId);
    List<MembershipJpo> findByTravelClubJpo_Id(String clubId);
    List<MembershipJpo> findByMemberJpo_Id(String memberId);
    List<MembershipJpo> findByEmail(String email);
}
