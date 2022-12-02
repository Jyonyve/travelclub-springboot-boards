package io.namoosori.travelclub.web.service;

import io.namoosori.travelclub.web.aggregate.board.Comment;
import io.namoosori.travelclub.web.service.sdo.CommentCdo;
import io.namoosori.travelclub.web.shared.NameValueList;

import java.util.List;

public interface CommentService {

    String register(String postingId, CommentCdo commentCdo);
    Comment find(String commentId);
    List<Comment> findByPostingId(String postingId);
    void modify(String commentId, NameValueList nameValueList);
    void remove(String commentId);
}
