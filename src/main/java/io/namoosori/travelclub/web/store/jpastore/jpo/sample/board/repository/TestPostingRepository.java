package io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.repository;

import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestPostingJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestPostingRepository extends JpaRepository<TestPostingJpo, String> {
    List<TestPostingJpo> findAllByTestBoardJpo_Id(String boardId);
}
