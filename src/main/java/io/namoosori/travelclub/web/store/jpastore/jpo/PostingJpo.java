package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.board.Posting;
import io.namoosori.travelclub.web.service.BoardService;
import io.namoosori.travelclub.web.service.logic.BoardServiceLogic;
import io.namoosori.travelclub.web.service.logic.PostingServiceLogic;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "Posting")
@Entity
@NoArgsConstructor
public class PostingJpo {

    @Id
    @JoinColumn(name = "postingId")
    private String id;
    private String title;
    private String writerEmail;
    @Column(columnDefinition = "TEXT")
    private String contents;
    private String writtenDate;
    private int readCount;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "boardId")
    private SocialBoardJpo socialBoardJpo;
    @OneToMany(mappedBy = "postingJpo",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentsJpo> commentsJpos = new ArrayList<>();

    public PostingJpo(Posting posting) {
        BeanUtils.copyProperties(posting, this);
        this.socialBoardJpo = new SocialBoardJpo(BoardServiceLogic.getBoardServiceLogic().findById(posting.getBoardId()));
    }

    public Posting toDomain(){
        Posting posting = new Posting(this);
        posting.setBoardId(this.socialBoardJpo.getId());
        return posting;

    }
}
