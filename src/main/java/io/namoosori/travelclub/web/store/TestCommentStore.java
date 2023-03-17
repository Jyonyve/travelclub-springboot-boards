package io.namoosori.travelclub.web.store;

import io.namoosori.travelclub.web.aggregate.sample.board.TestComment;

import java.util.List;

public interface TestCommentStore {
    TestComment create(TestComment comment);
    TestComment retrieve(String commentId);
    List<TestComment> retrieveByPostingId(String postingId);
    void update(TestComment comment);
    void delete(String commentId);
    boolean exists(String commentId);
}
