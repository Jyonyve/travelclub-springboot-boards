package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.sample.board.TestBoard;
import io.namoosori.travelclub.web.service.sdo.TestBoardCdo;
import io.namoosori.travelclub.web.service.TestBoardService;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.TestBoardStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestBoardServiceLogic implements TestBoardService {
    //
    private static TestBoardService testBoardServiceLogic;
    private static TestBoardStore boardStore;

    private TestBoardServiceLogic(TestBoardStore boardStore){
        //
        this.boardStore = boardStore;
    }

    public static TestBoardService testServiceLogic(){
        //
        if(testBoardServiceLogic == null){
            testBoardServiceLogic = new TestBoardServiceLogic(boardStore);
        }
        return testBoardServiceLogic;
    }

    @Override
    public String registerBoard(TestBoardCdo boardCdo) {
        //
        TestBoard board = new TestBoard(boardCdo.getName(), boardCdo.getBoardKind());
        return boardStore.create(board);
    }

    @Override
    public TestBoard findById(String id) {
        return boardStore.retrieve(id);
    }

    @Override
    public List<TestBoard> findAll() {
        return null;
    }

    @Override
    public void modify(String boardId, NameValueList nameValueList) {

    }

    @Override
    public void remove(String boardId) {

    }

    @Override
    public boolean exists(String boardId) {
        return boardStore.exists(boardId);
    }
}
