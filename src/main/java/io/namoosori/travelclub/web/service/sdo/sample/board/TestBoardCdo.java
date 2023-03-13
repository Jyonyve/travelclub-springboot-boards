package io.namoosori.travelclub.web.service.sdo.sample.board;

import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TestBoardCdo implements Serializable {
    private String name;
    private BoardKind boardKind;

    public TestBoardCdo( String name, BoardKind boardKind) {
        this.name = name;
        this.boardKind = boardKind;
    }
}
