package io.namoosori.travelclub.web.aggregate.club.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum Role {

	MEMBER("ROLE_MEMBER", "Default Member"),
	ADMIN("ROLE_ADMIN", "Admin");

	private final String key;
	private final String title;

}