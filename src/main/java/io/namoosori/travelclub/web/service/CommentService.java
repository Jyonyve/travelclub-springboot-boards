package io.namoosori.travelclub.web.service;

import io.namoosori.travelclub.web.aggregate.board.Comment;
import io.namoosori.travelclub.web.aggregate.board.Posting;
import io.namoosori.travelclub.web.service.sdo.CommentCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.jpastore.jpo.CommentsJpo;

import java.util.List;

public interface CommentService {

    Comment register(String postingId, CommentCdo commentCdo);
    Comment find(String commentId);
    List<Comment> findAllByPostingId(String postingId);
    void modify(String commentId, Comment comment);
    void remove(String commentId);

}
