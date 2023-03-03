package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.TravelClub;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "TRAVEL_CLUB")//생성되는 테이블명을 변경하는 어노테이션
@NoArgsConstructor
public class TravelClubJpo {

    @Id
    @JoinColumn(name = "clubId")
    private String id;
    private String name;
    private String intro;
    private long foundationTime;
    @OneToMany(mappedBy = "travelClubJpo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SocialBoardJpo> socialBoardJpos = new ArrayList<>();
    @OneToMany(mappedBy = "travelClubJpo")
    private List<MembershipJpo> membershipJpos = new ArrayList<>();

    public TravelClubJpo(TravelClub travelClub){
        BeanUtils.copyProperties(travelClub, this);
        //1번째 객체의 프로퍼티들을 카피해서 2번째 객체의 프로퍼티에 넣어준다. (this.~ 가 많을때 유용)
    }

    public TravelClub toDomain(){
        TravelClub travelClub = new TravelClub(this.name, this.intro);
        travelClub.setId(this.id);
        travelClub.setFoundationTime(this.foundationTime);
        return travelClub;
    }
}
