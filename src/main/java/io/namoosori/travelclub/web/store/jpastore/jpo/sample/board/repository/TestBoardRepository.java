package io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.repository;

import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestBoardJpo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestBoardRepository extends JpaRepository<TestBoardJpo, String> {
}
