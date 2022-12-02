package io.namoosori.travelclub.web.service.sdo;

import lombok.Data;

import java.io.Serializable;
@Data
public class SocialBoardCdo implements Serializable {
    private String clubId;
    private String name;
    private String adminEmail;
    private String createDate;
}
