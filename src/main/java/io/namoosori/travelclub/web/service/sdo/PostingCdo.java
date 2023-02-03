package io.namoosori.travelclub.web.service.sdo;

import io.namoosori.travelclub.web.util.helper.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostingCdo implements Serializable {
    private String title;
    private String writerEmail;
    private String contents;
    private String writtenDate;
    private int readCount;

    public PostingCdo(String title, String writerEmail, String contents, String boardId) {
        this.title = title;
        this.writerEmail = writerEmail;
        this.contents = contents;
        this.writtenDate = DateUtil.today();
        this.readCount = 0;
    }
}
