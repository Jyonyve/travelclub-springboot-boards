package io.namoosori.travelclub.web.store;

import io.namoosori.travelclub.web.aggregate.sample.board.TestBoard;

public interface TestBoardStore {
    String create(TestBoard board);
    TestBoard retrieve(String boardId);
    void update(TestBoard board);
    void delete(String boardId);
    boolean exists(String boardId);


}
