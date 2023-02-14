package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.board.Comment;
import io.namoosori.travelclub.web.service.CommentService;
import io.namoosori.travelclub.web.service.sdo.CommentCdo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postingId}")
    public Comment register(@PathVariable String postingId, @RequestBody CommentCdo commentCdo){
        Comment comment = commentService.register(postingId, commentCdo);
        System.out.println(comment.getId());
        return comment;
    }

    @GetMapping("/{commentId}")
    public Comment find(@PathVariable String commentId){
        return commentService.find(commentId);
    }

    @GetMapping("/all/{postingId}")
    List<Comment> findAllByPostingId(@PathVariable String postingId){
        return commentService.findAllByPostingId(postingId);
    }

    @PutMapping("/{commentId}")
    void modify(@PathVariable String commentId, @RequestBody Comment comment){
        commentService.modify(commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    void remove(@PathVariable String commentId){
        commentService.remove(commentId);
    }
}
