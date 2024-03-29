package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.store.jpastore.jpo.SocialBoardJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<SocialBoardJpo, String> {
    List<SocialBoardJpo> findAllByName(String name);
    SocialBoardJpo findByTravelClubJpo_IdAndBoardKind(String clubId, BoardKind boardKind);
    Optional<SocialBoardJpo> findByTravelClubJpo_Id(String clubId);
}
