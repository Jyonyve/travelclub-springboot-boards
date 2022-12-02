package io.namoosori.travelclub.web.service;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
import io.namoosori.travelclub.web.service.sdo.SocialBoardCdo;
import io.namoosori.travelclub.web.shared.NameValueList;

import java.util.List;

public interface BoardService {
    String registerBoard(SocialBoardCdo boardCdo);
    SocialBoard findBoardById(String id);
    List<SocialBoard> findBoardsByName(String name);
    List<SocialBoard> findAll();
    List<SocialBoard> findByClubName(String clubName);
    void modify(String boardId, NameValueList nameValueList);
    void remove(String boardId);
}
