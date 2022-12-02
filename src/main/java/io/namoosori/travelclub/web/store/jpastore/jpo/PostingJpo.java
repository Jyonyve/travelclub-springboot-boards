package io.namoosori.travelclub.web.store.jpastore.jpo;

import io.namoosori.travelclub.web.aggregate.board.Posting;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name = "Posting")
@Entity
public class PostingJpo {

    @Id
    private String id;
    private String title;
    private String writerEmail;
    private String contents;
    private String writtenDate;
    private int readCount;
    private String boardId;

    public PostingJpo(Posting posting) {
        BeanUtils.copyProperties(posting, this);
    }

    public Posting toDomain(){
        Posting posting = new Posting(title, writerEmail, contents, boardId);
        posting.setReadCount(readCount);
        posting.setWrittenDate(writtenDate);
        posting.setId(id);

        return posting;

    }
}
