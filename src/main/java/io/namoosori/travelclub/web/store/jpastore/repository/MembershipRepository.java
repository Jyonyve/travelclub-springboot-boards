package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.store.jpastore.jpo.MembershipJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<MembershipJpo, String> {

    List<MembershipJpo> findAllByName(String name);
    MembershipJpo findByClubIdAndMemberId(String clubId, String memberId);
    List<MembershipJpo> findByClubId(String clubId);
    List<MembershipJpo> findByMemberId(String memberId);
    Optional<MembershipJpo> findByEmail(String email);
}
