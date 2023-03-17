package io.namoosori.travelclub.web.service.jpo;

import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.aggregate.sample.board.TestBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TestBoard")
public class TestBoardJpo {

    @Id
    private String id;
    private String name;
    private String createDate;
    private BoardKind boardKind;
    @OneToMany(mappedBy = "testBoardJpo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TestPostingJpo> testPostingJpos = new ArrayList<>();

    public TestBoardJpo(TestBoard testBoard){
        BeanUtils.copyProperties(testBoard, this);
    }

    public TestBoard toDomain(){
        TestBoard testBoard = new TestBoard(this);
        return testBoard;
    }


}
