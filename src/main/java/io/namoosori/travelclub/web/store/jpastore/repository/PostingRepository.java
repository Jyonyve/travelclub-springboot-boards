package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.service.jpo.PostingJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostingRepository extends JpaRepository<PostingJpo, String> {
    List<PostingJpo> findAllBySocialBoardJpo_Id(String boardId);
    List<PostingJpo> findAllBySocialBoardJpo_IdAndWriterEmail(String boardId, String writerEmail);
}
