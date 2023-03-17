package io.namoosori.travelclub.web.store;

import io.namoosori.travelclub.web.aggregate.sample.board.TestPosting;

import java.util.List;

public interface TestPostingStore {
    String create(TestPosting posting);
    TestPosting retrieve(String postingId);
    List<TestPosting> retrieveByBoardId(String boardId);
    void update(TestPosting posting);
    void delete(String postingId);
    boolean exists(String postingId);
}
