package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.MemberStore;
import io.namoosori.travelclub.web.util.exception.MemberDuplicationException;
import io.namoosori.travelclub.web.util.exception.NoSuchMemberException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MemberServiceLogic implements MemberService {
	//
	private static MemberStore memberStore;
	public static MemberServiceLogic memberServiceLogic;

	private MemberServiceLogic(MemberStore memberStore) {
		//
		this.memberStore = memberStore;
	}

	public static MemberServiceLogic getMemberServiceLogic(){
		if (memberServiceLogic == null){
			memberServiceLogic = new MemberServiceLogic(memberStore);
		}
		return memberServiceLogic;
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
				memberCdo.getPhoneNumber(),
				memberCdo.getProvider(),
				memberCdo.getRefreshToken()
		);
		member.checkValidation();
		memberStore.create(member);
		System.out.println("ServiceMemberId"+ member.getId());
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
}
