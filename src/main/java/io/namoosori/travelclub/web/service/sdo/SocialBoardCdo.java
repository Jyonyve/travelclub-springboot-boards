package io.namoosori.travelclub.web.service.sdo;

import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
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
    private String createDate;
    private BoardKind boardKind;
}
