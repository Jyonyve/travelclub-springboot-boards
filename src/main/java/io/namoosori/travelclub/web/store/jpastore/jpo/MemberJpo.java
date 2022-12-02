package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.vo.Address;
import io.namoosori.travelclub.web.aggregate.club.vo.AddressType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Community_Member")
@NoArgsConstructor
public class MemberJpo {

    @Id
    private String id;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String birthday;

    private String addresses;

    public MemberJpo(CommunityMember member) {
        BeanUtils.copyProperties(member, this, "addresses");
        member.getAddresses().stream().map(Address::toString).forEach(address -> this.addresses = address);
    }

    public CommunityMember toDomain(){
        CommunityMember member = new CommunityMember(this.email, this.name, this.phoneNumber);
        member.setId(id);
        member.setBirthDay(this.birthday);
        member.setNickName(nickname);
        member.setAddresses(splitAddress(addresses));

        return member;
    }

    public List<Address> splitAddress(String addresses) {
        String[] add = addresses.split("^(:*/)$", 5);
        Address address1 = new Address(add[0],add[1], add[2], add[3], AddressType.valueOf(add[4]));

        List<Address> list = new ArrayList<>();
        list.add(address1);
        return list;
    }
}
