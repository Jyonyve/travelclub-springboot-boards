package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.service.jpo.CommentsJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentsJpo, String> {
    List<CommentsJpo> findAllByPostingJpo_Id(String postingId);
    CommentsJpo findByCommentNumber(int number);
    CommentsJpo findTopByOrderByCommentNumberDesc();
}
