package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.store.jpastore.jpo.SocialBoardJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<SocialBoardJpo, String> {
    List<SocialBoardJpo> findAllByName(String name);
}
