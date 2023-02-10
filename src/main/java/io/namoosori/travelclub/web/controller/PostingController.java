package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.board.Comment;
import io.namoosori.travelclub.web.aggregate.board.Posting;
import io.namoosori.travelclub.web.service.CommentService;
import io.namoosori.travelclub.web.service.PostingService;
import io.namoosori.travelclub.web.service.logic.BoardServiceLogic;
import io.namoosori.travelclub.web.service.logic.CommentServiceLogic;
import io.namoosori.travelclub.web.service.logic.PostingServiceLogic;
import io.namoosori.travelclub.web.service.sdo.CommentCdo;
import io.namoosori.travelclub.web.service.sdo.PostingCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.util.security.GoogleAuthentification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost:3000", "http://localhost:8080", "https://localhost:3000", "https://localhost:8080"},
        methods = {RequestMethod.HEAD, RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = {"Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization", "Access-Control-Allow-Credentials", "X-AUTH-TOKEN"}
)
@RestController
@RequestMapping("/board/posting")
public class PostingController {
    private PostingService postingService;
    private CommentService commentService;
    private GoogleAuthentification googleAuthentification;

    public PostingController() {
        this.commentService = CommentServiceLogic.getCommentServiceLogic();
        this.postingService = PostingServiceLogic.getPostingServiceLogic();
        googleAuthentification = GoogleAuthentification.getGoogleAuthentification();
    }

    @GetMapping("/{postingId}")
    public Posting findById(@PathVariable String postingId){
        return postingService.findById(postingId);
    }


    //comment
    @GetMapping("/{postingId}/all")
    public List<Comment> findAllByPostingId(@PathVariable String postingId){
        List<Comment> comments = commentService.findAllByPostingId(postingId);
        comments.stream().forEach(comment -> System.out.println(comment.getId()));
        return comments;
    }

    @PostMapping("/comment")
    public Comment registerNewComment(@RequestBody CommentCdo commentCdo, @RequestHeader("Authorization") String idToken){
        idToken = idToken.substring(7);
        Map<String, Object> payload = googleAuthentification.JWTTokenDecoder(idToken);
        String email = String.valueOf(payload.get("email"));
        System.out.println(email);
        System.out.println(commentCdo.getPostingId());
        commentCdo.setWriterEmail(email);
        return commentService.register(commentCdo.getPostingId(), commentCdo);
    }

    @PutMapping("/{postingId}/{commentNumber}")
    public void modify(@PathVariable String postingId, @PathVariable int commentNumber, @RequestBody Comment comment){
        commentService.modify(postingId+"/"+commentNumber, comment);
    }

    @DeleteMapping("/{postingId}/{commentNumber}")
    void remove(@PathVariable String postingId, @PathVariable int commentNumber){
        commentService.remove(postingId+"/"+commentNumber);
    }


//
//    @PutMapping("/{postingId}")
//    void modify(@PathVariable String postingId, @RequestBody NameValueList nameValueList){
//        postingService.modify(postingId, nameValueList);
//    }
//

}
