package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.board.Comment;
import io.namoosori.travelclub.web.service.CommentService;
import io.namoosori.travelclub.web.service.sdo.CommentCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.CommentStore;
import io.namoosori.travelclub.web.store.PostingStore;
import io.namoosori.travelclub.web.util.exception.NoSuchCommentException;
import io.namoosori.travelclub.web.util.exception.NoSuchPostingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceLogic implements CommentService {

    private CommentStore commentStore;
    private PostingStore postingStore;

    @Override
    public String register(String postingId, CommentCdo commentCdo) {
        if(postingStore.retrieve(postingId)==null){
            throw new NoSuchPostingException("there are no posting with the id: "+postingId);
        }
        Comment comment = new Comment(commentCdo.getWriter(), commentCdo.getContents(), postingId);

        return commentStore.create(comment);
    }

    @Override
    public Comment find(String commentId) {
        Comment comment = commentStore.retrieve(commentId);
        if(comment == null){
            throw new NoSuchCommentException("there are no comment with that id: " +commentId);
        }
        return comment;
    }

    @Override
    public List<Comment> findByPostingId(String postingId) {
        List<Comment> comments = commentStore.retrieveByPostingId(postingId);

        return comments;
    }

    @Override
    public void modify(String commentId, NameValueList nameValueList) {

        Comment comment = commentStore.retrieve(commentId);
        if(comment == null){
            throw new NoSuchCommentException("there are no comment with that id: " +commentId);
        }
        comment.modifyValues(nameValueList);
        commentStore.update(comment);

    }

    @Override
    public void remove(String commentId) {
        Comment comment = commentStore.retrieve(commentId);
        if(comment == null){
            throw new NoSuchCommentException("there are no comment with that id: " +commentId);
        }
        commentStore.delete(commentId);
    }
}
