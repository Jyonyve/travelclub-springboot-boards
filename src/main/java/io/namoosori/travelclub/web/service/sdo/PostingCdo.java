package io.namoosori.travelclub.web.service.sdo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostingCdo implements Serializable {
    private String title;
    private String writerEmail;
    private String contents;
    private String boardId;
}
