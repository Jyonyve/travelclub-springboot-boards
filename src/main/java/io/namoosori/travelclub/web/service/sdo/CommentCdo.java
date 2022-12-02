package io.namoosori.travelclub.web.service.sdo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentCdo implements Serializable {

    private String usid;
    private String writer;
    private String contents;
    private String postingId;
}
