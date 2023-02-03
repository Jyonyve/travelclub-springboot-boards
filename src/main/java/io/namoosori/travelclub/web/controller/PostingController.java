package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.board.Posting;
import io.namoosori.travelclub.web.service.PostingService;
import io.namoosori.travelclub.web.service.sdo.PostingCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posting")
public class PostingController {
    private PostingService postingService;

    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }


    @PostMapping("/{boardId}")
    public String register(@PathVariable String boardId, @RequestBody PostingCdo postingCdo){
        return postingService.register(boardId, postingCdo);
    }

    @GetMapping("/{postingId}")
    public Posting find(@PathVariable String postingId){
        return postingService.find(postingId);
    }

    @GetMapping
    List<Posting> findByBoardId(@RequestParam String boardId){
        return postingService.findByBoardId(boardId);
    }

    @PutMapping("/{postingId}")
    void modify(@PathVariable String postingId, @RequestBody NameValueList nameValueList){
        postingService.modify(postingId, nameValueList);
    }

    @DeleteMapping("/{postingId}")
    void remove(String postingId){
        postingService.remove(postingId);
    }
}
