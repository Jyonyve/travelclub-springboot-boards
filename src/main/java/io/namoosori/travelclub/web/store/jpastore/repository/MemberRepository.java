package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.store.jpastore.jpo.MemberJpo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberJpo, String> {
    List<MemberJpo> findAllByName(String name);
    UserDetails findByEmail(String email);
    Optional<MemberJpo> findByEmailAndProvider(String email, String Provider);
}
