package io.namoosori.travelclub.web.service;


import io.namoosori.travelclub.web.aggregate.sample.board.TestBoard;
import io.namoosori.travelclub.web.service.sdo.TestBoardCdo;
import io.namoosori.travelclub.web.shared.NameValueList;

import java.util.List;

public interface TestBoardService {
    String registerBoard(TestBoardCdo boardCdo);
    TestBoard findById(String id);
    List<TestBoard> findAll();
    void modify(String boardId, NameValueList nameValueList);
    void remove(String boardId);
    boolean exists(String boardId);
}
