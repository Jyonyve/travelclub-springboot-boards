package io.namoosori.travelclub.web.service.jpo;

import io.namoosori.travelclub.web.aggregate.sample.board.TestPosting;
import io.namoosori.travelclub.web.service.logic.TestBoardServiceLogic;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "TestPosting")
@Entity
@NoArgsConstructor
public class TestPostingJpo {

    @Id
    @JoinColumn(name = "postingId")
    private String id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String contents;
    private String writtenDate;
    private int readCount;
    private int postingNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private TestBoardJpo testBoardJpo;
    @OneToMany(mappedBy = "testPostingJpo",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TestCommentsJpo> testCommentsJpos = new ArrayList<>();

    public TestPostingJpo(TestPosting posting) {
        BeanUtils.copyProperties(posting, this);
        System.out.println(posting.getBoardId());
        this.testBoardJpo = new TestBoardJpo(TestBoardServiceLogic.testServiceLogic().findById(posting.getBoardId()));
    }

    public TestPosting toDomain(){
        TestPosting testPosting = new TestPosting(this);
        testPosting.setBoardId(this.testBoardJpo.getId());
        return testPosting;

    }
}
