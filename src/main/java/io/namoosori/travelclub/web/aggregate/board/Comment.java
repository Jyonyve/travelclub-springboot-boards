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
public class Comment extends Entity {

    private String writer;
    private String contents;
    private String writtenDate;

    private String postingId;

    public Comment(String writer, String contents, String postingId) {
        super();
        this.writer = writer;
        this.contents = contents;
        this.writtenDate = DateUtil.today();
        this.postingId = postingId;
    }

    public void modifyValues(NameValueList nameValues) {
        //
        for (NameValue nameValue : nameValues.getNameValues()) {
            String value = nameValue.getValue();
            switch (nameValue.getName()) {
                case "contents":
                    this.contents = value;
                    break;
            }
        }
    }

    public static Comment sample() {
        //
        String writer="mymy2@nextree.co.kr";
        String contents = "comment contents!";
        String postingId = "";
        return new Comment(writer, contents, postingId);
    }

    public static void main(String[] args) {
        //
        System.out.println(new Gson().toJson(sample()));
    }
}
