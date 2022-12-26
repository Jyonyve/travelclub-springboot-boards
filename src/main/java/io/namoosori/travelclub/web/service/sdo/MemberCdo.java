package io.namoosori.travelclub.web.service.sdo;

import io.namoosori.travelclub.web.aggregate.Entity;
import io.namoosori.travelclub.web.aggregate.club.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCdo extends Entity {
    //
    private String email;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String birthDay;
    private Address addresses;
}
