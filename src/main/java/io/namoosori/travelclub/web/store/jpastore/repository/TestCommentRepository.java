package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.service.jpo.TestCommentsJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCommentRepository extends JpaRepository<TestCommentsJpo, String> {
    //
    List<TestCommentsJpo> findAllByTestPostingJpo_Id(String postingId);
    TestCommentsJpo findTopByOrderByCommentNumberDesc();

}
