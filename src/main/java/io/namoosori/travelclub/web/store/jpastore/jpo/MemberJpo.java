package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.Address;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Community_Member")
@NoArgsConstructor
public class MemberJpo {

    @Id
    @JoinColumn(name="Gen_id")
    private String id;
    private String email;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String birthday;
    @OneToOne(fetch = FetchType.LAZY)

    private Address addresses;

    public MemberJpo(CommunityMember member) {
        BeanUtils.copyProperties(member, this, "addresses");
        //member.getAddresses().stream().map(Address::toString).forEach(address -> this.addresses = address);
    }

    public CommunityMember toDomain(){
        CommunityMember member = new CommunityMember(this.email, this.name, this.phoneNumber);
        member.setId(id);
        member.setBirthDay(this.birthday);
        member.setNickName(nickname);
        //member.setAddresses(splitAddress(addresses));

        return member;
    }

    //@ElementCollection 과 @Embeddable 어노테이션을 사용하면 스트링 변환을 거치지 않고 주소를 종속 테이블로 만들 수 있음...
//    public List<Address> splitAddress(String addresses) {
//        Pattern pattern = Pattern.compile("[:](.*?)[/]");
//        Matcher matcher = pattern.matcher(addresses);
//        List<String> list = new ArrayList<>();
//        while (matcher.find()){
//            String add = matcher.group(1);
//            list.add(add);
//        }
//        Address address = new Address(list.get(0), list.get(1), list.get(2), list.get(3), AddressType.valueOf(list.get(4)));
//        List<Address> addressList = new ArrayList<>();
//        addressList.add(address);
//
//        return addressList;
//    }
}
