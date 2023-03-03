package io.namoosori.travelclub.web.service.sdo;

import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.util.helper.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class SocialBoardCdo implements Serializable {
    private String clubId;
    private String name;
    private BoardKind boardKind;

    public SocialBoardCdo(String clubId, String name, BoardKind boardKind) {
        this.clubId = clubId;
        this.name = name;
        this.boardKind = boardKind;
    }
}
