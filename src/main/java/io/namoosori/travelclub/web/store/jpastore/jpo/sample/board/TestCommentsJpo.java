package io.namoosori.travelclub.web.store.jpastore.jpo.sample.board;

import io.namoosori.travelclub.web.aggregate.board.Comment;
import io.namoosori.travelclub.web.aggregate.sample.board.TestComment;
import io.namoosori.travelclub.web.service.logic.TestPostingServiceLogic;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TestComments")
public class TestCommentsJpo {
    @Id
    private String id;
    private String writerEmail;
    private String contents;
    private String writtenDate;
    private int commentNumber;
    //private String postingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testPostingId")
    private TestPostingJpo testPostingJpo;

    public TestCommentsJpo(TestComment comment){
        BeanUtils.copyProperties(comment, this);
        this.testPostingJpo = new TestPostingJpo(TestPostingServiceLogic.getPostingServiceLogic().findById(comment.getPostingId()));
    }

    public TestComment toDomain(){
        TestComment comment = new TestComment( contents, testPostingJpo.getId(), commentNumber);
        comment.setId(testPostingJpo.getId()+"/"+commentNumber);
        return comment;
    }
}
