package io.namoosori.travelclub.web.aggregate.board;

import com.google.gson.Gson;
import io.namoosori.travelclub.web.aggregate.Entity;
import io.namoosori.travelclub.web.shared.NameValue;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.util.helper.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
            String value = String.valueOf(nameValue.getValue());
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

    public static SocialBoard sample() {
        //
        String clubId="";
        String adminEmail ="mymy2@nextree.co.kr";
        String name = "Social Board Name!";
        return new SocialBoard(clubId, name, adminEmail);
    }

    public static void main(String[] args) {
        //
        System.out.println(new Gson().toJson(sample()));
    }


}
