package io.namoosori.travelclub.web.service.jpo;

import io.namoosori.travelclub.web.aggregate.board.Comment;
import io.namoosori.travelclub.web.service.logic.PostingServiceLogic;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Comments")
public class CommentsJpo {
    @Id
    private String id;
    private String writerEmail;
    private String contents;
    private String writtenDate;
    private int commentNumber;
    //private String postingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postingId")
    private PostingJpo postingJpo;

    public CommentsJpo(Comment comment){
        BeanUtils.copyProperties(comment, this);
        this.postingJpo = new PostingJpo(PostingServiceLogic.getPostingServiceLogic().findById(comment.getPostingId()));

    }

    public Comment toDomain(){
        Comment comment = new Comment(writerEmail, contents, postingJpo.getId(), commentNumber);
        comment.setId(postingJpo.getId()+"/"+commentNumber);
        return comment;
    }
}
