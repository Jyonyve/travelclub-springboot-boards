package io.namoosori.travelclub.web.service.sdo;

import lombok.Data;

import java.io.Serializable;
@Data
public class PostingCdo implements Serializable {
    private String usid;
    private String title;
    private String writerEmail;
    private String contents;
    private String boardId;
}
