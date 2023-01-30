package io.namoosori.travelclub.web.aggregate.club;

import io.namoosori.travelclub.web.aggregate.Entity;
import io.namoosori.travelclub.web.aggregate.club.vo.Roles;
import io.namoosori.travelclub.web.shared.NameValue;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.util.helper.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
public class Membership extends Entity {
	//
	private String clubId;
	private String memberId;
	@Enumerated(EnumType.STRING)
	private Roles roles;
	private String joinDate;
	private String name;
	private String email;
	private String password;


	public Membership(String id) {
		//
		super(id);
	}

	public Membership(String clubId, String memberId, String name, String email, String password) {
		//
		this.clubId = clubId; 
		this.memberId = memberId;
		this.email = email;
		this.name = name;
		this.password = password;
		this.roles = Roles.valueOf(Roles.MEMBER.getKey());
		this.joinDate = DateUtil.today();
	}

	@Override
	public String toString() {
		//
		StringBuilder builder = new StringBuilder();

		builder.append("club Id:").append(clubId);
		builder.append(", member Id:").append(memberId);
		builder.append(", member Name:").append(name);
		builder.append(", role:").append(roles.name());
		builder.append(", join date:").append(joinDate);

		return builder.toString();
	}

	public void modifyValues(NameValueList nameValueList) {
		//
		for (NameValue nameValue : nameValueList.getNameValues()) {
			String value = String.valueOf(nameValue.getValue());
			switch (nameValue.getName()) {
				case "role":
					this.roles = Roles.valueOf(value);
					break;
			}
		}
	}

//	public static Membership sample() {
//		//
//		return new Membership(
//				TravelClub.sample().getId(),
//				CommunityMember.sample().getId(),
//				CommunityMember.sample().getName(),
//				CommunityMember.sample().getEmail()
//		);
//	}
//
//	public static void main(String[] args) {
//		//
//		System.out.println(new Gson().toJson(sample()));
//	}
}