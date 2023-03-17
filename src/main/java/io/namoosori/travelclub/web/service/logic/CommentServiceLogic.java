package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.board.Comment;
import io.namoosori.travelclub.web.service.CommentService;
import io.namoosori.travelclub.web.service.sdo.CommentCdo;
import io.namoosori.travelclub.web.store.CommentStore;
import io.namoosori.travelclub.web.store.PostingStore;
import io.namoosori.travelclub.web.util.exception.NoSuchCommentException;
import io.namoosori.travelclub.web.util.exception.NoSuchPostingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceLogic implements CommentService {

    private static CommentStore commentStore;
    private static PostingStore postingStore;
    private static CommentService commentService;

    private CommentServiceLogic(CommentStore commentStore, PostingStore postingStore) {
        this.commentStore = commentStore;
        this.postingStore = postingStore;
    }

    public static CommentService getCommentServiceLogic(){
        if(commentService == null){
            commentService = new CommentServiceLogic(commentStore, postingStore);
        }
        return commentService;

    }

    @Override
    public Comment register(String postingId, CommentCdo commentCdo) {
        List<Comment> comments = commentStore.retrieveByPostingId(postingId);
        if(comments == null){
            throw new NoSuchPostingException("there are no posting with the id: "+postingId);
        }
        Comment comment = new Comment(commentCdo.getWriterEmail(), commentCdo.getContents(), postingId);
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
    public List<Comment> findAllByPostingId(String postingId) {
        List<Comment> comments = commentStore.retrieveByPostingId(postingId);

        return comments;
    }

    @Override
    public void modify(String commentId, Comment comment) {
        commentStore.update(comment);

    }

    @Override
    public void remove(String commentId) {
        commentStore.delete(commentId);
    }
}
