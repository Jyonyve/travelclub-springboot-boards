package io.namoosori.travelclub.web.store;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;

import java.util.List;
import java.util.Optional;

public interface BoardStore {
    String create(SocialBoard board);
    SocialBoard retrieve(String boardId);
    SocialBoard retrieveByClubIdAndBoardKind(String clubId, BoardKind boardKind);
    Optional<SocialBoard> retrieveByClubId(String clubId);
    List<SocialBoard> retrieveByName(String boardName);
    List<SocialBoard> retrieveAll();
    void update(SocialBoard board);
    void delete(String boardId);
    boolean exists(String boardId);


}
