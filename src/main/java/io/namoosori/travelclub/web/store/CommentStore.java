package io.namoosori.travelclub.web.store;

import io.namoosori.travelclub.web.aggregate.board.Comment;

import java.util.List;

public interface CommentStore {

    String create(Comment comment);
    Comment retrieve(String commentId);
    List<Comment> retrieveByPostingId(String postingId);
    void update(Comment comment);
    void delete(String commentId);
    boolean exists(String commentId);
}
