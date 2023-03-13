package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.aggregate.club.TravelClub;
import io.namoosori.travelclub.web.service.BoardService;
import io.namoosori.travelclub.web.service.sdo.SocialBoardCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.BoardStore;
import io.namoosori.travelclub.web.store.ClubStore;
import io.namoosori.travelclub.web.util.exception.NoSuchBoardException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardServiceLogic implements BoardService {
    //
    private static BoardStore boardStore;
    private static ClubStore clubStore;
    private static BoardService boardServiceLogic;

    private BoardServiceLogic (BoardStore boardStore, ClubStore clubStore){
        BoardServiceLogic.boardStore =boardStore;
        BoardServiceLogic.clubStore = clubStore;
    }

    public static BoardService getBoardServiceLogic(){
        if (boardServiceLogic == null){
            boardServiceLogic = new BoardServiceLogic(boardStore, clubStore);
        }
        return boardServiceLogic;
    }

    @Override
    public String registerBoard(@NotNull SocialBoardCdo boardCdo) {
        //
        SocialBoard board = new SocialBoard(boardCdo.getClubId(),boardCdo.getName(),boardCdo.getBoardKind());
        return boardStore.create(board);

    }

    @Override
    public SocialBoard findById(String id) {
        SocialBoard board = boardStore.retrieve(id);
        if(board == null){
            throw new NoSuchBoardException("No such board in storage");
        }
        return board;
    }

    @Override
    public SocialBoard findByClubIdAndBoardKind(String clubId, BoardKind boardKind) {
        SocialBoard board = boardStore.retrieveByClubIdAndBoardKind(clubId, boardKind);
        if(board == null){
            throw new NoSuchBoardException("No such board in storage");
        }
        return board;
    }

    @Override
    public List<SocialBoard> findBoardsByName(String name) {

        List<SocialBoard> list = boardStore.retrieveByName(name);

        if(list.size() == 0){
            throw new NoSuchBoardException("No such board with that name" + name);
        }
        return list;
    }

    @Override
    public List<SocialBoard> findAll() {
        List<SocialBoard> list = boardStore.retrieveAll();

        if(list.size() == 0){
            throw new NoSuchBoardException("No board in storage");
        }
        return list;
    }

    @Override
    public List<SocialBoard> findByClubName(String clubName) {
        List<TravelClub> clubs = clubStore.retrieveByName(clubName);

        List<SocialBoard> boards = clubs.stream().map(TravelClub::getId).collect(Collectors.toList())
                                        .stream().map(id -> findById(id)).collect(Collectors.toList());


        return boards;
    }

    @Override
    public Optional<SocialBoard> findByClubId(String clubId) {

        Optional<SocialBoard> board =boardStore.retrieveByClubId(clubId);

        if(!board.isPresent()){
            throw new NoSuchBoardException("No board in storage");
        }
        return board;
    }

    @Override
    public void modify(String boardId, NameValueList nameValueList) {
        SocialBoard board = boardStore.retrieve(boardId);
        if(boardStore.exists(boardId)) {
            board.modifyValues(nameValueList);
            boardStore.update(board);
        }
        else {
            throw new NoSuchBoardException("no board with that id: "+boardId);
        }
    }

    @Override
    public void remove(String boardId) {
        SocialBoard board = boardStore.retrieve(boardId);
        if(board == null){
            throw new NoSuchBoardException("no board with that id: "+boardId);
        }

        boardStore.delete(boardId);

    }
}
