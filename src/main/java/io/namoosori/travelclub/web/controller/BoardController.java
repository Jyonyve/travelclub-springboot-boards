package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
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
    public String registerBoard(@RequestBody SocialBoardCdo boardCdo){
        return boardService.registerBoard(boardCdo);
    }

    @GetMapping("/{id}")
    public SocialBoard findBoardById(@PathVariable String id){
        return boardService.findBoardById(id);
    }

    @GetMapping("/all/{name}")
    public List<SocialBoard> findBoardsByName(@PathVariable String name){
        return boardService.findBoardsByName(name);
    }

    @GetMapping("/all")
    public List<SocialBoard> findAll(){
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
