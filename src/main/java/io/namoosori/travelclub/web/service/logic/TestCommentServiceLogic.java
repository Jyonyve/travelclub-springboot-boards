package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.sample.board.TestComment;
import io.namoosori.travelclub.web.service.sdo.sample.board.TestCommentCdo;
import io.namoosori.travelclub.web.service.sdo.sample.board.TestCommentService;
import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestCommentStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestPostingStore;
import io.namoosori.travelclub.web.util.exception.NoSuchCommentException;
import io.namoosori.travelclub.web.util.exception.NoSuchPostingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCommentServiceLogic implements TestCommentService {

    private static TestCommentService commentService;
    private static TestCommentStore commentStore;
    private static TestPostingStore postingStore;

    private TestCommentServiceLogic() {
        super();
    }

    private TestCommentServiceLogic(TestCommentStore commentStore, TestPostingStore postingStore) {
        this.commentStore = commentStore;
        this.postingStore = postingStore;
    }

    public static TestCommentService getCommentServiceLogic(){
        if(commentService == null){
            commentService = new TestCommentServiceLogic(commentStore, postingStore);
        }
        return commentService;

    }

    @Override
    public TestComment register(String postingId, TestCommentCdo commentCdo) {
        List<TestComment> comments = commentStore.retrieveByPostingId(postingId);
        if(comments == null){
            throw new NoSuchPostingException("there are no posting with the id: "+postingId);
        }
        TestComment comment = new TestComment(commentCdo.getWriterEmail(), commentCdo.getContents(), postingId, comments.size()+1);
        return commentStore.create(comment);
    }

    @Override
    public TestComment findById(String commentId) {
        //
        TestComment comment = commentStore.retrieve(commentId);
        if(comment == null){
            throw new NoSuchCommentException("there are no comment with that id: " +commentId);
        }
        return comment;
    }

    @Override
    public List<TestComment> findAllByPostingId(String postingId) {
        //
        List<TestComment> comments = commentStore.retrieveByPostingId(postingId);

        return comments;
    }

    @Override
    public void modify(String commentId, TestComment comment) {
        commentStore.update(comment);
    }

    @Override
    public void remove(String commentId) {
        commentStore.delete(commentId);
    }
}
