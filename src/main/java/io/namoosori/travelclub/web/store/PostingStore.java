package io.namoosori.travelclub.web.store;

import io.namoosori.travelclub.web.aggregate.board.Posting;

import java.util.List;

public interface PostingStore {
    String create(Posting posting);
    Posting retrieve(String postingId);
    List<Posting> retrieveByBoardId(String boardId);
    void update(Posting posting);
    void delete(String postingId);
    boolean exists(String postingId);
}
