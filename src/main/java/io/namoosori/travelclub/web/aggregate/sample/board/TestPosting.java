package io.namoosori.travelclub.web.aggregate.sample.board;

import io.namoosori.travelclub.web.aggregate.Entity;
import io.namoosori.travelclub.web.service.sdo.PostingCdo;
import io.namoosori.travelclub.web.shared.NameValue;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.jpastore.jpo.PostingJpo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class TestPosting {

    private String id;
    private String title;
    private String writerEmail;
    private String contents;
    private String writtenDate;
    private int readCount;
    private String boardId;
    private int postingNumber;

    public TestPosting(String boardId, PostingCdo postingCdo){
        BeanUtils.copyProperties(postingCdo, this);
        this.boardId=boardId;
        this.id = boardId+postingNumber;
    }

    public TestPosting(PostingJpo postingJpo){
        BeanUtils.copyProperties(postingJpo, this);
    }


    public void modifyValues(NameValueList nameValues) {
        //
        for (NameValue nameValue : nameValues.getNameValues()) {
            String value = String.valueOf(nameValue.getValue());
            switch (nameValue.getName()) {
                case "contents":
                    this.contents = value;
                    break;
                case "title":
                    this.title = value;
            }
        }
    }

//    public static Posting sample() {
//        //
//
//        String title = "new posting title~";
//        String contents = "new posting contents~~!!";
//        String writerEmail = "mymy1@nextree.co.kr";
//        String boardId ="";
//        return new Posting(title, writerEmail, contents, boardId);
//    }
//
//    public static void main(String[] args) {
//        //
//        System.out.println(new Gson().toJson(sample()));
//    }
}
