package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.board.Posting;
import io.namoosori.travelclub.web.store.PostingStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.PostingJpo;
import io.namoosori.travelclub.web.store.jpastore.repository.BoardRepository;
import io.namoosori.travelclub.web.store.jpastore.repository.PostingRepository;
import io.namoosori.travelclub.web.util.exception.NoSuchPostingException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostingJpaStore implements PostingStore {

    private PostingRepository postingRepository;
    private BoardRepository boardRepository;

    public PostingJpaStore(PostingRepository postingRepository, BoardRepository boardRepository) {
        this.postingRepository = postingRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public String create(Posting posting) {
        PostingJpo postingJpo = new PostingJpo(posting);
        postingJpo.setSocialBoardJpo(boardRepository.findById(posting.getBoardId()).get());
        System.out.println(posting.getBoardId());
        postingRepository.save(postingJpo);
        return posting.getId();
    }


    @Override
    public Posting retrieve(String postingId) {
        Optional<PostingJpo> postingJpo = postingRepository.findById(postingId);
        if(postingJpo == null){
            throw new NoSuchPostingException("No Posting like this : "+postingId);
        }

        return postingJpo.get().toDomain();
    }

    @Override
    public List<Posting> retrieveByBoardId(String boardId) {
        List<PostingJpo> postingJpos = postingRepository.findAllBySocialBoardJpo_Id(boardId);
        return postingJpos.stream().map(PostingJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Posting> retrieveByBoardIdAndWriterEmail(String boardId, String writerEmail) {
        List<PostingJpo> personalPostingJpos = postingRepository.findAllBySocialBoardJpo_IdAndWriterEmail(boardId, writerEmail);
        return personalPostingJpos.stream().map(PostingJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public void update(Posting posting) {
        postingRepository.save(new PostingJpo(posting));
    }

    @Override
    public void delete(String postingId) {

        if(!postingRepository.existsById(postingId)){
            throw new NoSuchPostingException("No such Posting in this storage: "+postingId);
        }
        postingRepository.deleteById(postingId);
    }

    @Override
    public boolean exists(String postingId) {
        return postingRepository.existsById(postingId);
    }
}
