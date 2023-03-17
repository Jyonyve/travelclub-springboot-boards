package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.service.jpo.TestPostingJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestPostingRepository extends JpaRepository<TestPostingJpo, String> {
    List<TestPostingJpo> findAllByTestBoardJpo_Id(String boardId);
}
