package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.sample.board.TestComment;
import io.namoosori.travelclub.web.store.TestCommentStore;
import io.namoosori.travelclub.web.service.jpo.TestCommentsJpo;
import io.namoosori.travelclub.web.store.jpastore.repository.TestCommentRepository;
import io.namoosori.travelclub.web.util.exception.NoSuchCommentException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TestCommentJpaStore implements TestCommentStore {

    private TestCommentRepository commentRepository;

    public TestCommentJpaStore(TestCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public TestCommentsJpo getMaxCommentNumber(){
        return commentRepository.findTopByOrderByCommentNumberDesc();
    }

    @Override
    public TestComment create(TestComment comment) {;
        if (getMaxCommentNumber() == null) {
            comment.setCommentNumber(0);
        } else {
            comment.setCommentNumber(getMaxCommentNumber().getCommentNumber()+1);
        }
        comment.setId(comment.getPostingId()+"/"+comment.getCommentNumber());
        commentRepository.save(new TestCommentsJpo(comment));
        return comment;
    }

    @Override
    public TestComment retrieve(String commentId) {
        Optional<TestCommentsJpo> commentsJpo= commentRepository.findById(commentId);
        if(commentsJpo.isEmpty()){
            throw new NoSuchCommentException("No such comment id: "+commentId);
        }
        System.out.println(commentsJpo.get().getCommentNumber());
        return commentsJpo.get().toDomain();
    }

    @Override
    public List<TestComment> retrieveByPostingId(String postingId) {
        List<TestCommentsJpo> commentsJpos = commentRepository.findAllByTestPostingJpo_Id(postingId);

        return commentsJpos.stream().map(TestCommentsJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public void update(TestComment comment) {
        commentRepository.save(new TestCommentsJpo(comment));
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
