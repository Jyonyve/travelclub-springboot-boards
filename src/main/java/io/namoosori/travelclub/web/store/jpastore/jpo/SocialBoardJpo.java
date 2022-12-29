package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
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
    private String adminEmail;
    private String createDate;
    @OneToMany(mappedBy = "socialBoardJpo")
    private List<PostingJpo> postingJpos = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY) @MapsId @JoinColumn(name = "id")
    private TravelClubJpo travelClubJpo;

    public SocialBoardJpo(SocialBoard socialBoard){
        BeanUtils.copyProperties(socialBoard, this);
    }

    public SocialBoard toDomain(){
        SocialBoard socialBoard = new SocialBoard(travelClubJpo.getId(), name, adminEmail);
        socialBoard.setCreateDate(createDate);

        return socialBoard;
    }


}
