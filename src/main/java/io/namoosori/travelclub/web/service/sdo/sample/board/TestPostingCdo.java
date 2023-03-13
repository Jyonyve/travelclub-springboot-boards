package io.namoosori.travelclub.web.service.sdo.sample.board;

import io.namoosori.travelclub.web.util.helper.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TestPostingCdo implements Serializable {
    private String title;
    private String contents;
    private String writtenDate;
    private int readCount;
    private int postingNumber;

    public TestPostingCdo(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.writtenDate = DateUtil.today();
        this.readCount = 0;
        this.postingNumber = 0;

    }
}
