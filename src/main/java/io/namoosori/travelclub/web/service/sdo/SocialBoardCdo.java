package io.namoosori.travelclub.web.service.sdo;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class SocialBoardCdo implements Serializable {
    private String clubId;
    private String name;
    private String adminEmail;
    private String createDate;
}
