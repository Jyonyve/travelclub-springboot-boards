package io.namoosori.travelclub.web.service.sdo;

import io.namoosori.travelclub.web.aggregate.club.vo.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCdo implements Serializable {
    //
    private String email;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String birthDay;
    private List<Address> addresses;
}
