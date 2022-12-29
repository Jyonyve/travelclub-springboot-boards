package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.board.Posting;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "Posting")
@Entity
public class PostingJpo {

    @Id
    @JoinColumn(name = "postingId")
    private String id;
    private String title;
    private String writerEmail;
    private String contents;
    private String writtenDate;
    private int readCount;
    //private String boardId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "boardId")
    private SocialBoardJpo socialBoardJpo;
    @OneToMany(mappedBy = "postingJpo")
    private List<CommentsJpo> commentsJpos = new ArrayList<>();

    public PostingJpo(Posting posting) {
        BeanUtils.copyProperties(posting, this);
    }

    public Posting toDomain(){
        Posting posting = new Posting(title, writerEmail, contents, socialBoardJpo.getId());
        posting.setReadCount(readCount);
        posting.setWrittenDate(writtenDate);
        posting.setId(id);

        return posting;

    }
}
