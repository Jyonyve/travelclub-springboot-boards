package io.namoosori.travelclub.web.service;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.shared.NameValueList;

import java.util.List;

public interface MemberService {
	//
	String register(MemberCdo memberCdo);
    CommunityMember findMemberById(String memberId);
	CommunityMember findMemberByEmail(String memberEmail);
	List<CommunityMember> findMembersByName(String name);
	List<CommunityMember> findAll();
	void modifyMember(String memberId, NameValueList member);
	void removeMember(String memberId);
}