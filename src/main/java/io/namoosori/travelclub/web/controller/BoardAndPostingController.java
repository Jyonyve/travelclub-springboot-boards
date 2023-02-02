package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.board.Posting;
import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.service.BoardService;
import io.namoosori.travelclub.web.service.PostingService;
import io.namoosori.travelclub.web.service.logic.BoardServiceLogic;
import io.namoosori.travelclub.web.service.logic.PostingServiceLogic;
import io.namoosori.travelclub.web.service.sdo.PostingCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board")
public class BoardAndPostingController {

    private BoardService boardService;
    private PostingService postingService;

    public BoardAndPostingController() {
        this.boardService = BoardServiceLogic.getBoardServiceLogic();
        this.postingService = PostingServiceLogic.getPostingServiceLogic();
    }

    //All board is automatically generated : cannot make new board by user.
    // ~/board/boardId @PostMapping goes to insert new posting.
    @PostMapping("/{clubId}/{boardKind}")
    public String registerNewPosting(@PathVariable("clubId") String clubId, @PathVariable("boardKind") String boardKind, @RequestBody PostingCdo postingCdo){
        String boardId = clubId+"/"+boardKind;
        return postingService.register(boardId, postingCdo);
    }

//    @GetMapping("/{id}")
//    public SocialBoard findBoardById(@PathVariable String id){
//        return boardService.findBoardById(id);
//    }

    @GetMapping("/{clubId}/{boardKind}")
    public Map<String, Object> findByClubIdAndBoardKind(@PathVariable("clubId") String clubId, @PathVariable("boardKind") String boardKind){
        System.out.println("clubId , boardKind : " + clubId + ", " +BoardKind.valueOf(boardKind));
        Map<String, Object> boardInfoAndPostingList = new HashMap<>();
        SocialBoard oneBoardInfo = boardService.findByClubIdAndBoardKind(clubId, BoardKind.valueOf(boardKind));
        List<Posting> postings = PostingServiceLogic.getPostingServiceLogic().findByBoardId(oneBoardInfo.getId());
        boardInfoAndPostingList.put("board", oneBoardInfo);
        boardInfoAndPostingList.put("postings", postings);
        return boardInfoAndPostingList;
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
