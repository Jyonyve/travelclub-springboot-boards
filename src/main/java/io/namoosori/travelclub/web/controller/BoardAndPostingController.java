package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.board.Comment;
import io.namoosori.travelclub.web.aggregate.board.Posting;
import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.service.BoardService;
import io.namoosori.travelclub.web.service.CommentService;
import io.namoosori.travelclub.web.service.PostingService;
import io.namoosori.travelclub.web.service.logic.BoardServiceLogic;
import io.namoosori.travelclub.web.service.logic.CommentServiceLogic;
import io.namoosori.travelclub.web.service.logic.PostingServiceLogic;
import io.namoosori.travelclub.web.service.sdo.CommentCdo;
import io.namoosori.travelclub.web.service.sdo.PostingCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.jpastore.jpo.CommentsJpo;
import io.namoosori.travelclub.web.store.jpastore.jpo.PostingJpo;
import io.namoosori.travelclub.web.util.security.GoogleAuthentification;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost:3000", "http://localhost:8080", "https://localhost:3000", "https://localhost:8080"},
        methods = {RequestMethod.HEAD, RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = {"Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization", "Access-Control-Allow-Credentials", "X-AUTH-TOKEN"}
)
@RestController
@RequestMapping("/board")
public class BoardAndPostingController {

    private BoardService boardService;
    private PostingService postingService;
    private GoogleAuthentification googleAuthentification;

    public BoardAndPostingController() {
        this.boardService = BoardServiceLogic.getBoardServiceLogic();
        this.postingService = PostingServiceLogic.getPostingServiceLogic();
        googleAuthentification = GoogleAuthentification.getGoogleAuthentification();
    }

//    No CRUD for Boards : automatically generated and no modifying, deleting




    //All board is automatically generated : cannot make new board by user.
    // ~/board/boardId @PostMapping goes to insert new posting.
    @PostMapping("/{clubId}/{boardKind}")
    public String registerNewPosting(@PathVariable("clubId") String clubId, @PathVariable("boardKind") String boardKind,
                                     @RequestBody PostingCdo postingCdo, @RequestHeader("Authorization") String idToken) {
        idToken = idToken.substring(7);
        Map<String, Object> payload = googleAuthentification.JWTTokenDecoder(idToken);
        String email = String.valueOf(payload.get("email"));
        String boardId = clubId + "/" + boardKind;

        postingCdo.setWriterEmail(email);
        return postingService.register(boardId, postingCdo);
    }

    //same as /{boardId}
    @GetMapping("/{clubId}/{boardKind}")
    public Map<String, Object> findByClubIdAndBoardKind(
            @PathVariable("clubId") String clubId, @PathVariable("boardKind") String boardKind, @RequestHeader("Authorization") String idToken) {

        idToken = idToken.substring(7);
        Map<String, Object> payload = googleAuthentification.JWTTokenDecoder(idToken);
        String email = String.valueOf(payload.get("email"));

        Map<String, Object> boardInfoAndPostingList = new HashMap<>();
        SocialBoard oneBoardInfo = boardService.findByClubIdAndBoardKind(clubId, BoardKind.valueOf(boardKind));

        switch (BoardKind.valueOf(boardKind)){
            case QNABOARD:
                if(!googleAuthentification.adminChecker(email)){
                    List<Posting> postings = postingService.findBySocialBoardJpo_IdAndWriterEmail(oneBoardInfo.getId(), email);
                    boardInfoAndPostingList.put("board", oneBoardInfo);
                    boardInfoAndPostingList.put("postings", postings);
                    return boardInfoAndPostingList;
                }
            default:
                List<Posting> postings = postingService.findByBoardId(oneBoardInfo.getId());
                boardInfoAndPostingList.put("board", oneBoardInfo);
                boardInfoAndPostingList.put("postings", postings);
                return boardInfoAndPostingList;
        }

    }

    //    @GetMapping("/{clubId}")
//    public List<SocialBoard> findAll(@PathVariable String clubId){
//        return boardService.findAll();
//    }
    @PutMapping("/{postingId}")
    public void modify(@PathVariable String postingId, @RequestBody Posting posting) {
        postingService.modify(postingId, posting);
    }

    @DeleteMapping("/{postingId}")
    public void remove(@PathVariable String postingId) {
        postingService.remove(postingId);
    }
}