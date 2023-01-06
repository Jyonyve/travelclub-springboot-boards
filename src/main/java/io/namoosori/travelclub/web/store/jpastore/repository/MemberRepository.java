package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.store.jpastore.jpo.MemberJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberJpo, String> {
    List<MemberJpo> findAllByName(String name);
    MemberJpo findByEmail(String email);
    Optional<MemberJpo> findByEmailAndProvider(String email, String Provider);
}
