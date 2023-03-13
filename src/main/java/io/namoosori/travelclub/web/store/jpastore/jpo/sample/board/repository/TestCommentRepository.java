package io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.repository;

import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestCommentsJpo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCommentRepository extends JpaRepository<TestCommentsJpo, String> {
    //

}
