package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Social_Board")
public class SocialBoardJpo {

    @Id
    private String id;
    private String clubId;
    private String name;
    private String adminEmail;
    private String createDate;

    public SocialBoardJpo(SocialBoard socialBoard){
        BeanUtils.copyProperties(socialBoard, this);
    }

    public SocialBoard toDomain(){
        SocialBoard socialBoard = new SocialBoard(this.clubId, name, adminEmail);
        socialBoard.setCreateDate(createDate);
        socialBoard.setId(id);

        return socialBoard;
    }


}
