package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.board.Comment;
import io.namoosori.travelclub.web.store.CommentStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.CommentsJpo;
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

    @Override
    public String create(Comment comment) {
        commentRepository.save(new CommentsJpo(comment));
        return comment.getId();
    }

    @Override
    public Comment retrieve(String commentId) {
        Optional<CommentsJpo> commentsJpo= commentRepository.findById(commentId);
        if(commentsJpo == null){
            throw new NoSuchCommentException("No such comment id: "+commentId);
        }
        return commentsJpo.get().toDomain();
    }

    @Override
    public List<Comment> retrieveByPostingId(String postingId) {
        List<CommentsJpo> commentsJpos = commentRepository.findAllByPostingId(postingId);

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
