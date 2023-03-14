package io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.repository;

import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestCommentsJpo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestCommentRepository extends JpaRepository<TestCommentsJpo, String> {
    //
    List<TestCommentsJpo> findAllByTestPostingJpo_Id(String postingId);
    TestCommentsJpo findTopByOrderByCommentNumberDesc();

}
