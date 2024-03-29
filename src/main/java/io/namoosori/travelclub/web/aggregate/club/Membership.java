package io.namoosori.travelclub.web.aggregate.club;

import io.namoosori.travelclub.web.aggregate.club.vo.RoleInClub;
import io.namoosori.travelclub.web.aggregate.club.vo.Roles;
import io.namoosori.travelclub.web.service.sdo.MembershipCdo;
import io.namoosori.travelclub.web.shared.NameValue;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.jpastore.jpo.MembershipJpo;
import io.namoosori.travelclub.web.util.helper.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
public class Membership  {
	//
	private String id;
	private String clubId;
	private String memberId;
	@Enumerated(EnumType.STRING)
	private Roles roles;
	@Enumerated(EnumType.STRING)
	private RoleInClub roleInClub;
	private String joinDate;
	private String nickname;
	private String email;
	private String password;

	public Membership(MembershipCdo membershipCdo) {
		//
		BeanUtils.copyProperties(membershipCdo, this);
		this.roles = Roles.MEMBER;
		this.roleInClub = RoleInClub.Member;
		this.joinDate = DateUtil.today();
		this.id = clubId+"/"+email;
	}

	public Membership(MembershipJpo membershipJpo){
		BeanUtils.copyProperties(membershipJpo, this);
	}


//	@Override
//	public String toString() {
//		//
//		StringBuilder builder = new StringBuilder();
//
//		builder.append("club Id:").append(clubId);
//		builder.append(", member Id:").append(memberId);
//		builder.append(", member Name:").append(name);
//		builder.append(", role:").append(roles.name());
//		builder.append(", join date:").append(joinDate);
//
//		return builder.toString();
//	}

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