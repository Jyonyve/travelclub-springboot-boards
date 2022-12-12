package io.namoosori.travelclub.web.service;

import io.namoosori.travelclub.web.aggregate.club.TravelClub;
import io.namoosori.travelclub.web.service.sdo.TravelClubCdo;
import io.namoosori.travelclub.web.shared.NameValueList;

import java.util.List;

public interface ClubService {
	//
	String registerClub(TravelClubCdo club);
	TravelClub findClubById(String id);
	String findIdByReactId(String reactId);
	List<TravelClub> findClubsByName(String name);
	List<TravelClub> findAll();
	void modify(String clubId, NameValueList nameValues);
	void remove(String clubId);
}
