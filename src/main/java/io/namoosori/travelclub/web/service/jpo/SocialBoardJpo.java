package io.namoosori.travelclub.web.service.jpo;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.service.logic.ClubServiceLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Social_Board")
public class SocialBoardJpo {

    @Id
    private String id;
    private String name;
    private String createDate;
    private BoardKind boardKind;
    @OneToMany(mappedBy = "socialBoardJpo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostingJpo> postingJpos = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubId")
    //@MapsId @JoinColumn(name = "id")
    private TravelClubJpo travelClubJpo;

    public SocialBoardJpo(SocialBoard socialBoard){
        BeanUtils.copyProperties(socialBoard, this);
        this.travelClubJpo = new TravelClubJpo(ClubServiceLogic.getClubServiceLogic().findClubById(socialBoard.getClubId()));
    }

    public SocialBoard toDomain(){
        SocialBoard socialBoard = new SocialBoard(this);
        socialBoard.setClubId(this.travelClubJpo.getId());
        return socialBoard;
    }


}
