package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.aggregate.sample.board.TestBoard;
import io.namoosori.travelclub.web.aggregate.sample.board.TestComment;
import io.namoosori.travelclub.web.aggregate.sample.board.TestPosting;
import io.namoosori.travelclub.web.service.logic.TestBoardServiceLogic;
import io.namoosori.travelclub.web.service.logic.TestCommentServiceLogic;
import io.namoosori.travelclub.web.service.logic.TestPostingServiceLogic;
import io.namoosori.travelclub.web.service.sdo.sample.board.*;
import org.aspectj.lang.annotation.DeclareError;
import org.aspectj.weaver.ast.Test;
import org.hibernate.DuplicateMappingException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost:3000", "http://localhost:8080", "https://localhost:3000", "https://localhost:8080"},
        methods = {RequestMethod.HEAD, RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = {"Origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization", "Access-Control-Allow-Credentials", "X-AUTH-TOKEN"}
)
@RestController
@RequestMapping("/test")
public class TestController {

    private TestBoardService testBoardService;
    private TestPostingService testPostingService;
    private TestCommentService testCommentService;

    public TestController() {
        this.testBoardService = TestBoardServiceLogic.testServiceLogic();
        this.testPostingService = TestPostingServiceLogic.getPostingServiceLogic();
        this.testCommentService = TestCommentServiceLogic.getCommentServiceLogic();
    }

    @PostMapping("/{boardKind}")
    public void addSampleBoard(@PathVariable String boardKind){
        //
        if(testBoardService.exists(boardKind)){
            testBoardService.registerBoard(new TestBoardCdo("sample"+boardKind, BoardKind.valueOf(boardKind)));
        } else {
            throw new DuplicateMappingException(boardKind, "same board exists.");
        }
    }
    @GetMapping("/{boardKind}")
    public List<TestPosting> getSampleBoard(@PathVariable String boardKind){
        //
        List<TestPosting> postings = testPostingService.findByTestBoardId(boardKind);
        return postings;
    }

    @PostMapping("/{boardKind}/posting")
    public String addSamplePosting(@PathVariable String boardKind,
                                        @RequestBody TestPostingCdo postingCdo){
        //
        String id = testPostingService.register(boardKind, postingCdo);
        System.out.println(id);
        return id;
    }

    @GetMapping("/{boardKind}/{postingNumber}")
    public TestPosting fetchSamplePosting(@PathVariable("boardKind") String boardKind,
                                          @PathVariable("postingNumber") int postingNumber){
        //
        String postingId = boardKind+"/"+postingNumber;
        return testPostingService.findById(postingId);
    }

    @PutMapping("/{boardKind}/{postingNumber}")
    public void testModify(@PathVariable("boardKind") String boardKind,
                           @PathVariable("postingNumber") int postingNumber,
                            @RequestBody TestPosting testPosting){
        //
        testPostingService.modify(boardKind+"/"+postingNumber, testPosting);
    }

    @DeleteMapping("/{boardKind}/{postingNumber}")
    public void testDelete(@PathVariable("boardKind") String boardKind,
                           @PathVariable("postingNumber") int postingNumber){
        //
        testPostingService.remove(boardKind+"/"+postingNumber);
    }

    @PostMapping("/{boardKind}/{postingNumber}/comment")
    public TestComment addSampleComment(@PathVariable String boardKind,
                                 @PathVariable int postingNumber,
                                 @RequestBody TestCommentCdo commentCdo){
        return testCommentService.register(boardKind+"/"+postingNumber, commentCdo);
    }

    @GetMapping("/{boardKind}/{postingNumber}/all")
    public List<TestComment> fetchSampleComments(@PathVariable String boardKind, @PathVariable int postingNumber){
        return testCommentService.findAllByPostingId(boardKind+"/"+postingNumber);
    }

    @PutMapping("/{boardKind}/{postingNumber}/{commentNumber}")
    public void modifySampleComment(@PathVariable String boardKind, @PathVariable int postingNumber,
                                    @PathVariable int commentNumber, @RequestBody TestComment comment){
        testCommentService.modify(boardKind+"/"+postingNumber+"/"+commentNumber, comment);
    }

    @DeleteMapping("/{boardKind}/{postingNumber}/{commentNumber}")
    public void modifySampleComment(@PathVariable String boardKind, @PathVariable int postingNumber,
                                    @PathVariable int commentNumber){
        testCommentService.remove(boardKind+"/"+postingNumber+"/"+commentNumber);
    }
}