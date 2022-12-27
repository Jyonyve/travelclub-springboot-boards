package io.namoosori.travelclub.web.service.sdo;

import io.namoosori.travelclub.web.aggregate.club.vo.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressCdo implements Serializable {

    private String zipCode;
    private String zipAddress;
    private String streetAddress;
    private String country;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

}
