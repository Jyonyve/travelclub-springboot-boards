package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.board.Comment;
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
    private String writer;
    private String contents;
    private String writtenDate;
    //private String postingId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postingId")
    private PostingJpo postingJpo;

    public CommentsJpo(Comment comment){
        BeanUtils.copyProperties(comment, this);
    }

    public Comment toDomain(){
        Comment comment = new Comment(writer, contents, postingJpo.getId());
        comment.setWrittenDate(writtenDate);
        comment.setId(id);
        return comment;
    }
}
