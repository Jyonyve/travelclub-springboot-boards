package io.namoosori.travelclub.web.store;

import io.namoosori.travelclub.web.aggregate.club.CommunityMember;
import io.namoosori.travelclub.web.aggregate.club.vo.Roles;

import java.util.List;

public interface MemberStore {
	//
	String create(CommunityMember member);
	CommunityMember retrieve(String memberId);
	CommunityMember retrieveByEmail(String email);
	CommunityMember retrieveByIdToken(String idToken);
	List<CommunityMember> retrieveAllByRoles(Roles roles);
	List<CommunityMember> retrieveByName(String name);
	List<CommunityMember> retrieveAll();
	void update(CommunityMember member);
	void delete(String email);
	
	boolean exists(String memberId);
}
