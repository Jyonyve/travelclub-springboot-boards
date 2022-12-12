package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.store.jpastore.jpo.TravelClubJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<TravelClubJpo, String> {

    //Repository 인터페이스끼리의 상속관계를 확인해보면 좋다.
    //제네릭으로 들어가는 클래스: <@Entity , type of Primary Key>

    List<TravelClubJpo> findAllByName(String name);
    //Query Method: 다양한 SELECT 옵션을 메소드 이름을 조합함으로써 정의할 수 있다.
    //개발자가 만든 Repository 인터페이스(여기)에 메소드를 정의하면 된다.
    TravelClubJpo findByReactId(String reactId);

}
