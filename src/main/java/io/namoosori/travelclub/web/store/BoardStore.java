package io.namoosori.travelclub.web.store;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;

import java.util.List;

public interface BoardStore {
    String create(SocialBoard board);
    SocialBoard retrieve(String boardId);
    List<SocialBoard> retrieveByName(String boardName);
    List<SocialBoard> retrieveAll();
    void update(SocialBoard board);
    void delete(String boardId);

    boolean exists(String boardId);
}
