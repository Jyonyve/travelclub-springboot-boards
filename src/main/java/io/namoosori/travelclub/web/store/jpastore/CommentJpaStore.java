package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.board.Comment;
import io.namoosori.travelclub.web.store.CommentStore;
import io.namoosori.travelclub.web.service.jpo.CommentsJpo;
import io.namoosori.travelclub.web.store.jpastore.repository.CommentRepository;
import io.namoosori.travelclub.web.util.exception.NoSuchCommentException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CommentJpaStore implements CommentStore {

    private CommentRepository commentRepository;

    public CommentJpaStore(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentsJpo getMaxCommentNumber(){
        return commentRepository.findTopByOrderByCommentNumberDesc();
    }

    @Override
    public Comment create(Comment comment) {
        CommentsJpo maxRow = getMaxCommentNumber();
        if(maxRow == null){
            comment.setCommentNumber(0);
        } else {
            comment.setCommentNumber(maxRow.getCommentNumber()+1);
        }
        comment.setId(comment.getPostingId()+"/"+comment.getCommentNumber());
        commentRepository.save(new CommentsJpo(comment));
        return comment;
    }

    @Override
    public Comment retrieve(String commentId) {
        Optional<CommentsJpo> commentsJpo= commentRepository.findById(commentId);
        if(commentsJpo.isEmpty()){
            throw new NoSuchCommentException("no such comment: "+commentId);
        }
        return commentsJpo.get().toDomain();
    }

    @Override
    public List<Comment> retrieveByPostingId(String postingId) {
        List<CommentsJpo> commentsJpos = commentRepository.findAllByPostingJpo_Id(postingId);

        return commentsJpos.stream().map(CommentsJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public void update(Comment comment) {
        commentRepository.save(new CommentsJpo(comment));
    }

    @Override
    public void delete(String commentId) {
        if(!commentRepository.existsById(commentId)) {
            throw new NoSuchCommentException("No Such Comment: "+commentId);
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public boolean exists(String commentId) {
        return commentRepository.existsById(commentId);
    }
}
