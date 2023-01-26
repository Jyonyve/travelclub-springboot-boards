package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.vo.Role;
import io.namoosori.travelclub.web.service.AddressService;
import io.namoosori.travelclub.web.store.MemberStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.AddressJpo;
import io.namoosori.travelclub.web.store.jpastore.jpo.MemberJpo;
import io.namoosori.travelclub.web.store.jpastore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemberJpaStore implements MemberStore {

    private MemberRepository memberRepository;

    public MemberJpaStore(MemberRepository memberRepository, AddressService addressService) {
        this.memberRepository = memberRepository;
    }

    @Override
    public String create(CommunityMember member) {
        MemberJpo memberJpo = new MemberJpo(member);

        AddressJpo addressJpo = new AddressJpo();
        addressJpo.setMemberJpo(memberJpo);
        memberJpo.setAddressJpo(addressJpo);
        memberJpo.setNickname("default");
        memberJpo.setPassword("default");
        memberJpo.setProvider("direct");

        memberRepository.save(memberJpo);
        System.out.println("save Member ID : "+ memberJpo.getId());
        return memberJpo.getId();
    }

    @Override
    public CommunityMember retrieve(String memberId) {
        Optional<MemberJpo> memberJpo = memberRepository.findById(memberId);
        if(memberJpo.isPresent()){
            return memberJpo.get().toDomain();
        }
        else {
            throw new Error("no member like that!");
        }
    }

    public List<CommunityMember> retrieveAll(){
        List<MemberJpo> memberJpos = memberRepository.findAll();
        List<CommunityMember> communityMemberList = memberJpos.stream().map(MemberJpo::toDomain).collect(Collectors.toList());
        System.out.println(communityMemberList);
        return communityMemberList;
    }


    @Override
    public CommunityMember retrieveByEmail(String email) {
        UserDetails memberJpo = memberRepository.findByEmail(email);
        if(memberJpo == null){
            return null;
        }
        return ((MemberJpo)memberJpo).toDomain() ;
    }

    @Override
    public List<CommunityMember> retrieveByName(String name) {
        List<MemberJpo> memberJpos = memberRepository.findAllByName(name);
        return memberJpos.stream().map(MemberJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public void update(CommunityMember member) {
        memberRepository.save(new MemberJpo(member));
        //아이디가 있으면 업데이트를, 없으면 크리에이트를 해주는 메소드
    }

    @Override
    public void delete(String email) {
        memberRepository.deleteById(email);

    }

    @Override
    public boolean exists(String memberId) {
        return memberRepository.existsById(memberId);
    }
}
