package io.namoosori.travelclub.web.aggregate.board;

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
public class SocialBoard extends Entity {

    private String clubId;
    private String name;
    private String adminEmail;
    private String createDate;


    public SocialBoard(String clubId, String name, String adminEmail) {
        super();
        this.clubId = clubId;
        this.name = name;
        this.adminEmail = adminEmail;
        this.createDate = DateUtil.today();
    }


    public void modifyValues(NameValueList nameValueList) {
        //
        for (NameValue nameValue : nameValueList.getNameValues()) {
            String value = nameValue.getValue();
            switch (nameValue.getName()) {
                case "name":
                    this.name = value;
                    break;
                case "adminEmail":
                    this.adminEmail = value;
                    break;
            }
        }
    }


}
