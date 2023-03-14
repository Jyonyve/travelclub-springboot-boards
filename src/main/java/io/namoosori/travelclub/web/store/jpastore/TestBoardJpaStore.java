package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.sample.board.TestBoard;
import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestBoardJpo;
import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestBoardStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.repository.TestBoardRepository;
import io.namoosori.travelclub.web.util.exception.NoSuchBoardException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TestBoardJpaStore implements TestBoardStore {
    //
    private TestBoardRepository testBoardRepository;

    public TestBoardJpaStore(TestBoardRepository testBoardRepository){
        this.testBoardRepository = testBoardRepository;
    }

    @Override
    public String create(TestBoard testBoard){
        testBoardRepository.save(new TestBoardJpo(testBoard));

        return testBoard.getId();
    }

    @Override
    public TestBoard retrieve(String boardId) {
        Optional<TestBoardJpo> testBoardJpo = testBoardRepository.findById(boardId);
        if(testBoardJpo.isEmpty()){
            throw new NoSuchBoardException("No testboard is found.");
        }
        return testBoardJpo.get().toDomain();
    }

    @Override
    public void update(TestBoard board) {
        //
        testBoardRepository.save(new TestBoardJpo(board));
    }

    @Override
    public void delete(String boardId) {
        //
        if(!exists(boardId)){
            throw new NoSuchBoardException("no board like that!" +boardId);
        }
        testBoardRepository.deleteById(boardId);
    }

    @Override
    public boolean exists(String boardId) {
        return testBoardRepository.existsById(boardId);
    }
}
