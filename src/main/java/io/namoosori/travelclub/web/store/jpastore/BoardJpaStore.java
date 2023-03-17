package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.store.BoardStore;
import io.namoosori.travelclub.web.service.jpo.SocialBoardJpo;
import io.namoosori.travelclub.web.store.jpastore.repository.BoardRepository;
import io.namoosori.travelclub.web.util.exception.NoSuchBoardException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BoardJpaStore implements BoardStore {

    private BoardRepository boardRepository;

    public BoardJpaStore(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public String create(SocialBoard board) {
        boardRepository.save(new SocialBoardJpo(board));
        return board.getId();
    }

    @Override
    public SocialBoard retrieve(String boardId) {
        Optional<SocialBoardJpo> socialBoardJpo = boardRepository.findById(boardId);
        if(socialBoardJpo == null){
            throw new NoSuchBoardException("No Board is found"+boardId);
        }
        return socialBoardJpo.get().toDomain();
    }

    @Override
    public SocialBoard retrieveByClubIdAndBoardKind(String clubId, BoardKind boardKind) {
        SocialBoardJpo socialBoardJpo = boardRepository.findByTravelClubJpo_IdAndBoardKind(clubId, boardKind);
        if(socialBoardJpo == null){
            throw new NoSuchBoardException("No Board is found");
        }
        return socialBoardJpo.toDomain();
    }

    @Override
    public List<SocialBoard> retrieveByName(String boardName) {
        List<SocialBoardJpo> socialBoardJpos = boardRepository.findAllByName(boardName);
        return socialBoardJpos.stream().map(SocialBoardJpo::toDomain).collect(Collectors.toList());
    }

    public Optional<SocialBoard> retrieveByClubId(String clubId){
        Optional<SocialBoardJpo> socialBoardJpo = boardRepository.findByTravelClubJpo_Id(clubId);
        return socialBoardJpo.map(SocialBoardJpo::toDomain);
    }

    @Override
    public List<SocialBoard> retrieveAll() {
        List<SocialBoardJpo> socialBoardJpos = boardRepository.findAll();
        return socialBoardJpos.stream().map(SocialBoardJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public void update(SocialBoard board) {
        boardRepository.save(new SocialBoardJpo(board));

    }

    @Override
    public void delete(String boardId) {
        if(!boardRepository.existsById(boardId)){
            throw new NoSuchBoardException("no board like that!" +boardId);
        };
        boardRepository.deleteById(boardId);
    }

    @Override
    public boolean exists(String boardId) {
        return boardRepository.existsById(boardId);
    }

}