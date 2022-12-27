package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.service.AddressService;
import io.namoosori.travelclub.web.store.MemberStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.AddressJpo;
import io.namoosori.travelclub.web.store.jpastore.jpo.MemberJpo;
import io.namoosori.travelclub.web.store.jpastore.repository.MemberRepository;
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
        MemberJpo memberJpo = new MemberJpo((member));

        AddressJpo addressJpo = new AddressJpo();
        addressJpo.setMemberJpo(memberJpo);
        memberJpo.setAddressJpo(addressJpo);

        memberRepository.save(memberJpo);

        return memberJpo.getId();
    }

    @Override
    public CommunityMember retrieve(String memberId) {
        Optional<MemberJpo> memberJpo = memberRepository.findById(memberId);

        return memberJpo.get().toDomain();
    }

    public List<CommunityMember> retrieveAll(){
        List<MemberJpo> memberJpos = memberRepository.findAll();
        return memberJpos.stream().map(MemberJpo::toDomain).collect(Collectors.toList());
    }


    @Override
    public CommunityMember retrieveByEmail(String email) {
        Optional<MemberJpo> memberJpo = Optional.ofNullable(memberRepository.findByEmail(email));
        return memberJpo.map(MemberJpo::toDomain).orElse(null);
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
