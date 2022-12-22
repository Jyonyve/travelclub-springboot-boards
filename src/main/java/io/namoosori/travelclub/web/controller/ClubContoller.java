package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.aggregate.club.TravelClub;
import io.namoosori.travelclub.web.service.ClubService;
import io.namoosori.travelclub.web.service.sdo.TravelClubCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.jpastore.jpo.TravelClubJpo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController //spring ioc에 이 클래스를 컨트롤러 빈으로 등록할게. +@ResponseBody 어노테이션을 포함
                // (@Controller는 JSP view 페이지가 있어야 쓸수있음)
@RequestMapping("/club") //이 클래스가 호출하는 모든 메소드의 url 앞에 /club이 들어간다는 뜻.
public class ClubContoller {

//    @GetMapping("/test") //Get 메소드를 써서 접근할거야. 그리고 접근을 위해 이 url (/test) 을 쓸 거야,
//    public String test(){
//        return "Hello Spring MVC!";
//    }

    //사용할 서비스 클래스를 변수에 담아서 선언
    private ClubService clubService;

    //생성자
    public ClubContoller(ClubService clubService) {
        this.clubService = clubService; // spring ioc 컨테이너가 알아서 찾아서 ClubServiceLogic이라는 클래스를 꽂아준다 -> 컨트롤러가 실제 클래스는 모르게 됨
    }


    @PostMapping //새 클럽을 등록할거니까 post방식으로 올거야. 그걸 club이라는 url에 맵핑할게.
    public String register(@RequestBody TravelClubCdo travelClubCdo){ //cdo의 내용이 post방식이라서 url이 아니라 body에 담겨서 올거야. 그래서 @RequestBody를 붙여줌.
        return clubService.registerClub(travelClubCdo); //servicelogic에서 아이디를 스트링값으로 반환해주는 작업을 했음. 받아서 리턴해주면 됨.
    }


    @GetMapping // 조회할 때에는 Get을 쓴다.
    public List<TravelClub> findAll(){
        return clubService.findAll();
    }

    @GetMapping("/{clubid}") //get방식으로 데이터를 받을 때에는 중괄호{path}에 있는 variable 을 받겠다는 뜻으로 @PathViriable을 씀
    public TravelClub find(@PathVariable String clubid){
        return clubService.findClubById(clubid);
    }

   // @GetMapping("/club/{clubName}") <<이렇게 설계를 해 버리면 컴퓨터는 clubName과 clubId를 포맷상으로 구분하지 못해서 어떤 메소드를 호출할지 몰라 오류를 냄.
    @GetMapping("/?name=") //localhost:8090/club?name=JavaTravelClub3
    public List<TravelClub> findByName(@RequestParam String name){ //@RequestParam :  ? 이후 name을 key로, = 뒤의 value를 변수로 가지고 올게.
        return clubService.findClubsByName(name);
    }

//    @GetMapping("/react/{reactId}")
//    public String findIdByReactId(@PathVariable String reactId){
//        return clubService.findIdByReactId(reactId);
//    }

//    @PutMapping("/{clubId}")
//    public void modify(@PathVariable String clubId, @RequestBody NameValueList nameValueList){
//        clubService.modify(clubId, nameValueList);
//    }

    @PutMapping("/{id}")
    public void modifyReact(@PathVariable String id, @RequestBody TravelClubJpo travelClubJpo){
        NameValueList nameValueList = new NameValueList();
        nameValueList.addNameValue("name", travelClubJpo.getName());
        nameValueList.addNameValue("intro", travelClubJpo.getIntro());
        nameValueList.addNameValue("id", travelClubJpo.getId());
        clubService.modify(id, nameValueList);
    }

    @DeleteMapping("/{clubId}")
    public void delete(@PathVariable String clubId){
        clubService.remove(clubId);
    }

}
