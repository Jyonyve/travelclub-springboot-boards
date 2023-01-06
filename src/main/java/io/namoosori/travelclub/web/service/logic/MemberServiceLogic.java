package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.MemberStore;
import io.namoosori.travelclub.web.util.exception.MemberDuplicationException;
import io.namoosori.travelclub.web.util.exception.NoSuchMemberException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceLogic implements MemberService {
	//
	private MemberStore memberStore;

	public MemberServiceLogic(MemberStore memberStore) {
		//
		this.memberStore = memberStore;
	}

	@Override
	public String register(MemberCdo memberCdo) {
		//
		String email = memberCdo.getEmail();
		CommunityMember memberchk = memberStore.retrieveByEmail(email);
		if (memberchk != null) {
			throw new MemberDuplicationException("Member already exists with email: " + memberchk.getEmail());
		}
		CommunityMember member = new CommunityMember(
				memberCdo.getEmail(),
				memberCdo.getName(),
				memberCdo.getPhoneNumber()

		);
		member.checkValidation();
		memberStore.create(member);

		return member.getId();
	}

	@Override
	public CommunityMember findMemberById(String memberId) {
		//
		return memberStore.retrieve(memberId);
	}

	@Override
	public CommunityMember findMemberByEmail(String memberEmail) {
		//
		return memberStore.retrieveByEmail(memberEmail);
	}

	@Override
	public List<CommunityMember> findMembersByName(String name) {
		//
		return memberStore.retrieveByName(name);
	}

	@Override
	public List<CommunityMember> findAll() {
		return memberStore.retrieveAll();
	}

	@Override
	public void modifyMember(String memberId, NameValueList nameValueList) {
		//
		CommunityMember targetMember = memberStore.retrieve(memberId);

		if (targetMember == null) {
			throw new NoSuchMemberException("No such member with id " + memberId);
		}

		targetMember.modifyValues(nameValueList);

		memberStore.update(targetMember);
	}


	@Override
	public void removeMember(String memberId) {
		//
		if (!memberStore.exists(memberId)) {
			throw new NoSuchMemberException("No such member with id " + memberId);
		}

		memberStore.delete(memberId);
	}

	@Override
	//spring security의 UserDetailsService를 상속받아 구현하는 메소드
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<GrantedAuthority> authorities = new ArrayList<>();
		Optional<CommunityMember> rawMember = Optional.ofNullable(memberStore.retrieveByEmail(email));
		CommunityMember member = rawMember.orElse(null);

			if(email.equals("nthpopuptown@gmail.com")){
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			} else {
				authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
			}

			return new User(member.getEmail(), member.getPassword(), authorities);
		//loadUserByUsernae 메소드는 매개변수로 받은 정보를 이용해 회원을 조회, 회원 정보와 권한 정보가 담긴 User 클래스를 반환함.
		//User 클래스는 UserDetails 인터페이스를 구현하고 있다.
	//		Collection<? extends GrantedAuthority> getAuthorities();
	//		String getPassword();
	//		String getUsername();
	//		boolean isAccountNonExpired();
	//		boolean isAccountNonLocked();
	//		boolean isCredentialsNonExpired();
	//		boolean isEnabled();
		//UsernameNotFoundException을 던지고 있기 때문에, 유저를 조회할 시 null값이 나오면 알아서 처리해준다.
	}
}
