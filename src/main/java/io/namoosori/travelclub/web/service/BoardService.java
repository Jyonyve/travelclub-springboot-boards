package io.namoosori.travelclub.web.service;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.service.sdo.SocialBoardCdo;
import io.namoosori.travelclub.web.shared.NameValueList;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    String registerBoard(SocialBoardCdo boardCdo);
    SocialBoard findById(String id);
    SocialBoard findByClubIdAndBoardKind(String clubId, BoardKind boardKind);
    List<SocialBoard> findBoardsByName(String name);
    List<SocialBoard> findAll();
    List<SocialBoard> findByClubName(String clubName);
    Optional<SocialBoard> findByClubId(String ClubId);
    void modify(String boardId, NameValueList nameValueList);
    void remove(String boardId);
}
