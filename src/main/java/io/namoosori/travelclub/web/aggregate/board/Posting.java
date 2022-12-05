package io.namoosori.travelclub.web.aggregate.board;

import com.google.gson.Gson;
import io.namoosori.travelclub.web.aggregate.Entity;
import io.namoosori.travelclub.web.shared.NameValue;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.util.helper.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Posting extends Entity {

    private String title;
    private String writerEmail;
    private String contents;
    private String writtenDate;

    private int readCount = 0;

    private String boardId;


    public Posting(String title, String writerEmail, String contents, String boardId) {
        super();
        this.title = title;
        this.writerEmail = writerEmail;
        this.contents = contents;
        this.writtenDate = DateUtil.today();
        this.boardId = boardId;
    }


    public void modifyValues(NameValueList nameValues) {
        //
        for (NameValue nameValue : nameValues.getNameValues()) {
            String value = nameValue.getValue();
            switch (nameValue.getName()) {
                case "contents":
                    this.contents = value;
                    break;
                case "title":
                    this.title = value;
            }
        }
    }

    public static Posting sample() {
        //

        String title = "new posting title~";
        String contents = "new posting contents~~!!";
        String writerEmail = "mymy1@nextree.co.kr";
        String boardId ="";
        return new Posting(title, writerEmail, contents, boardId);
    }

    public static void main(String[] args) {
        //
        System.out.println(new Gson().toJson(sample()));
    }
}
