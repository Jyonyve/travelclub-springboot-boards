package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.service.BoardService;
import io.namoosori.travelclub.web.service.sdo.SocialBoardCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @PostMapping
    public String register(@RequestBody SocialBoardCdo boardCdo){
        return boardService.registerBoard(boardCdo);
    }

//    @GetMapping("/{id}")
//    public SocialBoard findBoardById(@PathVariable String id){
//        return boardService.findBoardById(id);
//    }

    @GetMapping("/{clubId}/{boardKind}")
    public SocialBoard findByClubIdAndBoardKind(@PathVariable("clubId") String clubId, @PathVariable("boardKind") BoardKind boardKind){
        return boardService.findByClubIdAndBoardKind(clubId, boardKind);
    }

    @GetMapping("/{clubId}")
    public List<SocialBoard> findAll(@PathVariable String clubId){
        return boardService.findAll();
    }

    @GetMapping
    public List<SocialBoard> findByClubName(@RequestParam String clubName){
        return boardService.findByClubName(clubName);
    }

    @PutMapping("/{boardId}")
    public void modify(@PathVariable String boardId, @RequestBody NameValueList nameValueList){
        boardService.modify(boardId, nameValueList);
    }

    @DeleteMapping("/{boardId}")
    public void remove(@PathVariable String boardId){
        boardService.remove(boardId);
    }
}
