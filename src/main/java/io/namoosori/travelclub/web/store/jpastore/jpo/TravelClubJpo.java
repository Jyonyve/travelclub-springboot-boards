package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.club.TravelClub;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "TRAVEL_CLUB")//생성되는 테이블명을 변경하는 어노테이션
@NoArgsConstructor
public class TravelClubJpo {

    @Id
    private String id;
    private String name;
    private String intro;
    private long foundationTime;
    private String reactId;

    public TravelClubJpo(TravelClub travelClub){
//        this.id = travelClub.getId();
//        this.name = travelClub.getName();
//        this.intro = travelClub.getIntro();
//        this.foundationTime = travelClub.getFoundationTime();

        BeanUtils.copyProperties(travelClub, this);
        //1번째 객체의 프로퍼티들을 카피해서 2번째 객체의 프로퍼티에 넣어준다. (this.~ 가 많을때 유용)
    }

    public TravelClub toDomain(){
        TravelClub travelClub = new TravelClub(this.name, this.intro, this.reactId);
        travelClub.setId(this.id);
        travelClub.setFoundationTime(this.foundationTime);
        return travelClub;
    }
}
