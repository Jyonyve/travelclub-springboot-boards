package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.Address;
import io.namoosori.travelclub.web.aggregate.club.vo.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Address")
public class AddressJpo {

    @Id
    private String id;
    private String zipCode;
    private String zipAddress;
    private String streetAddress;
    private String country;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    @OneToOne(fetch = FetchType.LAZY) @MapsId @JoinColumn(name = "id")
    private MemberJpo memberJpo;

    public AddressJpo(Address address){
        BeanUtils.copyProperties(address, this);
    }

    public Address toDomain(){
        Address address = new Address(zipCode, zipAddress, streetAddress, country, addressType, memberJpo.getId());
        return address;
    }

}
