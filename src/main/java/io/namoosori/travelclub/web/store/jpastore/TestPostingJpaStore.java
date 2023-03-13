package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.sample.board.TestPosting;
import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestPostingJpo;
import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestPostingStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.repository.TestBoardRepository;
import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.repository.TestPostingRepository;
import io.namoosori.travelclub.web.util.exception.NoSuchPostingException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TestPostingJpaStore implements TestPostingStore {
    //
    private TestPostingRepository postingRepository;
    private TestBoardRepository boardRepository;

    public TestPostingJpaStore(TestPostingRepository postingRepository, TestBoardRepository boardRepository) {
        this.boardRepository = boardRepository;
        this.postingRepository = postingRepository;
    }

    @Override
    public String create(TestPosting posting) {
        String boardId = posting.getBoardId();
        posting.setPostingNumber(postingRepository.findAllByTestBoardJpo_Id(boardId).size());
        String postingId = boardId +"/" + posting.getPostingNumber();
        posting.setId(postingId);

        TestPostingJpo postingJpo = new TestPostingJpo(posting);
        postingJpo.setTestBoardJpo(boardRepository.findById(boardId).get());
        System.out.println("postingId : " + postingId);

        postingRepository.save(postingJpo);
        return postingJpo.toDomain().getId();
    }


    @Override
    public TestPosting retrieve(String postingId) {
        Optional<TestPostingJpo> postingJpo = postingRepository.findById(postingId);
        if(postingJpo.isEmpty()){
            throw new NoSuchPostingException("No Posting like this : "+postingId);
        }
        return  postingJpo.get().toDomain();
    }

    @Override
    public List<TestPosting> retrieveByBoardId(String boardId) {
        List<TestPostingJpo> postingJpos = postingRepository.findAllByTestBoardJpo_Id(boardId);
        return postingJpos.stream().map(TestPostingJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public void update(TestPosting posting) {
        postingRepository.save(new TestPostingJpo(posting));
    }

    @Override
    public void delete(String postingId) {

        if(!postingRepository.existsById(postingId)){
            throw new NoSuchPostingException("No such Posting in this storage: "+postingId);
        }
        postingRepository.deleteById(postingId);
    }

    @Override
    public boolean exists(String postingId) {
        return postingRepository.existsById(postingId);
    }
}
