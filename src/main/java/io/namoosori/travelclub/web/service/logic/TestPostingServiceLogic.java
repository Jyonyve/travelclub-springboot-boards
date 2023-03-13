package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.sample.board.TestPosting;
import io.namoosori.travelclub.web.service.sdo.sample.board.TestPostingCdo;
import io.namoosori.travelclub.web.service.sdo.sample.board.TestPostingService;
import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestPostingStore;
import io.namoosori.travelclub.web.util.exception.NoSuchPostingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPostingServiceLogic implements TestPostingService {

    private static TestPostingService testPostingService;
    private static TestPostingStore postingStore;

    private TestPostingServiceLogic(TestPostingStore postingStore) {
        this.postingStore = postingStore;
    }

    public static TestPostingService getPostingServiceLogic(){
        if (testPostingService == null){
            testPostingService = new TestPostingServiceLogic(postingStore);
        }
        return testPostingService;
    }

    @Override
    public String register(String boardId, TestPostingCdo postingCdo) {
        //
        TestPosting posting = new TestPosting(boardId, postingCdo);
        System.out.println(posting.getId());
        return postingStore.create(posting);
    }

    @Override
    public TestPosting findById(String postingId) {
        //
        TestPosting posting = postingStore.retrieve(postingId);
        return posting;
    }

    @Override
    public List<TestPosting> findByTestBoardId(String boardId) {
        //
        List<TestPosting> postings = postingStore.retrieveByBoardId(boardId);
        return postings;
    }

    @Override
    public void modify(String postingId, TestPosting posting) {
        //
        postingStore.update(posting);
    }

    @Override
    public void remove(String postingId) {
        //
        if(!postingStore.exists(postingId)){
            throw new NoSuchPostingException("No such posting with id: "+postingId);
        }
        postingStore.delete(postingId);
    }
}
