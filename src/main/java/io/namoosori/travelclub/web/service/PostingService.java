package io.namoosori.travelclub.web.service;

import io.namoosori.travelclub.web.aggregate.board.Posting;
import io.namoosori.travelclub.web.service.sdo.PostingCdo;
import io.namoosori.travelclub.web.shared.NameValueList;

import java.util.List;


public interface PostingService {

    String register(String boardId, PostingCdo postingDto);

    Posting findById(String postingId);

    List<Posting> findByBoardId(String boardId);
    List<Posting> findBySocialBoardJpo_IdAndWriterEmail(String boardId, String writerEmail);

    void modify(String postingId, Posting posting);

    void remove(String postingId);
}
