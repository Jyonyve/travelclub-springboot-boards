package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.board.SocialBoard;
import io.namoosori.travelclub.web.aggregate.board.vo.BoardKind;
import io.namoosori.travelclub.web.aggregate.club.TravelClub;
import io.namoosori.travelclub.web.store.ClubStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.SocialBoardJpo;
import io.namoosori.travelclub.web.store.jpastore.jpo.TravelClubJpo;
import io.namoosori.travelclub.web.store.jpastore.repository.ClubRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ClubJpaStore implements ClubStore {

    private ClubRepository clubRepository;
    //Spring Data JPA가 구현해주는 ~Repository 계열의 메소드를 받아서 사용하게 된다.
    //Spring Data JPA 가 내부적으로 순수 JPA를 이용해 메소드를 구현해 줌. 개발자는 인터페이스만 정의하면 됨.
    public ClubJpaStore(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public String create(TravelClub club) {
        TravelClubJpo travelClubJpo = new TravelClubJpo(club);
        List<SocialBoardJpo> socialBoardJpos = new ArrayList<>();

        //auto-generated 3 boards
        SocialBoardJpo noticeBoardJpo = new SocialBoardJpo(new SocialBoard(club.getId(),"Notice Board", BoardKind.NOTICEBOARD));
            noticeBoardJpo.setTravelClubJpo(travelClubJpo);
        SocialBoardJpo socialBoardJpo = new SocialBoardJpo(new SocialBoard(club.getId(),"Social Board", BoardKind.SOCIALBOARD));
        socialBoardJpo.setTravelClubJpo(travelClubJpo);
        SocialBoardJpo qnaBoardJpo = new SocialBoardJpo(new SocialBoard(club.getId(),"QnA Board", BoardKind.QNABOARD));
            qnaBoardJpo.setTravelClubJpo((travelClubJpo));

        socialBoardJpos.add(socialBoardJpo);
        socialBoardJpos.add(noticeBoardJpo);
        socialBoardJpos.add(qnaBoardJpo);

        travelClubJpo.setSocialBoardJpos(socialBoardJpos);

        clubRepository.save(travelClubJpo);
        return club.getId();
    }

    @Override
    public TravelClub retrieve(String clubId) {
        Optional<TravelClubJpo> clubJpo = clubRepository.findById(clubId); //return 타입이 Optional이다. (nullable)

        return clubJpo != null ? clubJpo.map(TravelClubJpo::toDomain).get() : null;
    }

//    @Override
//    public String retrieveByReactId(String reactId) {
//        TravelClubJpo travelClubJpo = clubRepository.findIdByReactId(reactId);
//        return travelClubJpo.getId();
//    }

    @Override
    public List<TravelClub> retrieveByName(String name) {
        List<TravelClubJpo> clubJpos = clubRepository.findAllByName(name);
        return clubJpos.stream().map(TravelClubJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<TravelClub> retrieveAll() {

        List<TravelClubJpo> clubJpos = clubRepository.findAll();
        //clubJpos.stream().map(clubJpo -> clubJpo.toDomain()).collect(Collectors.toList());
        //return하는 내용과 똑같은 내용.
        //내부 객체 타입::그 객체가 가진 메소드 -> 이걸로 일괄로 변환해줘.

        return clubJpos.stream().map(TravelClubJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public void update(TravelClub club) {
        clubRepository.save(new TravelClubJpo(club)); // create와 update를 save 하나로 진행함 -> @Id 통해서 식별자가 없으면 생성, 있으면 수정
    }

    @Override
    public void delete(String clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public boolean exists(String clubId) {

        return clubRepository.existsById(clubId);
    }
}
