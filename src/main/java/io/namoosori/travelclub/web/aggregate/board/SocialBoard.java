package io.namoosori.travelclub.web.aggregate.board;

import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.shared.NameValue;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.service.jpo.SocialBoardJpo;
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
public class SocialBoard  {

    private String clubId;
    private String name;
    private String createDate;
    private BoardKind boardKind;
    private String id;


    public SocialBoard(String clubId, String name, BoardKind boardKind) {
        this.id = clubId+"/"+boardKind.name();
        this.clubId = clubId;
        this.name = name;
        this.boardKind = boardKind;
        this.createDate = DateUtil.today();
    }

    public SocialBoard(SocialBoardJpo socialBoardJpo){
        BeanUtils.copyProperties(socialBoardJpo, this);
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

//    public static SocialBoard sample() {
//        //
//        String clubId="";
//        String adminEmail ="mymy2@nextree.co.kr";
//        String name = "Social Board Name!";
//        return new SocialBoard(clubId, name, adminEmail);
//    }
//
//    public static void main(String[] args) {
//        //
//        System.out.println(new Gson().toJson(sample()));
//    }


}
