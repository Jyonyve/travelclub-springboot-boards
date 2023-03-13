package io.namoosori.travelclub.web.aggregate.sample.board;

import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.shared.NameValue;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.jpastore.jpo.sample.board.TestBoardJpo;
import io.namoosori.travelclub.web.util.helper.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestBoard {
    //
    private String name;
    private String createDate;
    private BoardKind boardKind;
    private String id;

    public TestBoard(String name, BoardKind boardKind) {
        //
        this.id = boardKind.name();
        this.name = name;
        this.boardKind = boardKind;
        this.createDate = DateUtil.today();
    }

    public TestBoard(TestBoardJpo testBoardJpo){
        //
        BeanUtils.copyProperties(testBoardJpo, this);
    }


    public void modifyValues(NameValueList nameValueList) {
        //
        for (NameValue nameValue : nameValueList.getNameValues()) {
            String value = String.valueOf(nameValue.getValue());
            switch (nameValue.getName()) {
                case "name":
                    this.name = value;
                    break;
            }
        }
    }
}
