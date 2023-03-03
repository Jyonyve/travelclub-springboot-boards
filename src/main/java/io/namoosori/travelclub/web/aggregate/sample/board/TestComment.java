package io.namoosori.travelclub.web.aggregate.sample.board;


import io.namoosori.travelclub.web.shared.NameValue;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.util.helper.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestComment {

    private String id;
    private String writerEmail;
    private String contents;
    private String writtenDate;
    private int commentNumber;
    private String postingId;

    public TestComment(String writerEmail, String contents, String postingId, int commentNumber) {
        this.writerEmail = writerEmail;
        this.contents = contents;
        this.writtenDate = DateUtil.today();
        this.postingId = postingId;
        this.commentNumber = commentNumber;
        this.id = postingId+"/"+commentNumber;
    }

    public TestComment(String writerEmail, String contents, String postingId) {
        super();
        this.writerEmail = writerEmail;
        this.contents = contents;
        this.writtenDate = DateUtil.today();
        this.postingId = postingId;
        this.commentNumber = 0;
        this.id = postingId+"/"+commentNumber;
    }

    public void modifyValues(NameValueList nameValues) {
        //
        for (NameValue nameValue : nameValues.getNameValues()) {
            String value = String.valueOf(nameValue.getValue());
            switch (nameValue.getName()) {
                case "contents":
                    this.contents = value;
                    break;
            }
        }
    }

//    public static Comment sample() {
//        //
//        String writer="mymy2@nextree.co.kr";
//        String contents = "comment contents!";
//        String postingId = "";
//        return new Comment(writer, contents, postingId);
//    }
//
//    public static void main(String[] args) {
//        //
//        System.out.println(new Gson().toJson(sample()));
//    }
}
