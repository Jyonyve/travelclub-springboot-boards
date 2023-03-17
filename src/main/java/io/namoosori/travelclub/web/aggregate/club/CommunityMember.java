package io.namoosori.travelclub.web.aggregate.club;

import io.namoosori.travelclub.web.aggregate.Entity;
import io.namoosori.travelclub.web.aggregate.club.vo.Provider;
import io.namoosori.travelclub.web.aggregate.club.vo.Roles;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.shared.NameValue;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.service.jpo.MemberJpo;
import io.namoosori.travelclub.web.util.exception.InvalidEmailException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityMember extends Entity{
	//
	//private String id;
	private String email;
	private String name;
	private String phoneNumber;
	private String nickName;
	private String birthDay;
	private Roles roles;
	private Provider provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지
	private String idToken;
	private String password;

	public CommunityMember(MemberJpo memberJpo){
		BeanUtils.copyProperties(memberJpo, this);
	}
	public CommunityMember(CommunityMember communityMember){
		BeanUtils.copyProperties(communityMember, this);
		this.idToken = "Backend Secret!";
	}
	public CommunityMember(MemberCdo memberCdo){
		BeanUtils.copyProperties(memberCdo, this);
		this.roles = memberCdo.getEmail().equals("nthpopuptown@gmail.com") ? Roles.ADMIN : Roles.MEMBER;
	}

	public CommunityMember (String email, String name, String phoneNumber, Provider provider, String idToken, Roles roles, String password){
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.provider = provider;
		this.idToken = idToken;
		this.roles = roles;
		this.password = password;
	}

    public CommunityMember(String email, String name, String phoneNumber) {
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
    }

	public String getRoleKey(){
		return this.roles.getKey();
	}

//    @Override
//	public String toString() {
//		//
//		StringBuilder builder = new StringBuilder();
//
//		builder.append("Name:").append(name);
//		builder.append(", email:").append(email);
//		builder.append(", nickname:").append(nickName);
//		builder.append(", phone number:").append(phoneNumber);
//		builder.append(", birthDay:").append(birthDay);
//		builder.append(", Address: ").append(Address.class.toString());
//
//		return builder.toString();
//	}

	public void checkValidation() {
		//
		checkEmailValidation(email);
	}

    private void checkEmailValidation(String email) {
		//
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		boolean valid = m.matches();

		if (!valid) {
			throw new InvalidEmailException("Email is invalid.");
		}
	}

	public void modifyValues(NameValueList nameValues) {
		//
		for (NameValue nameValue : nameValues.getNameValues()) {
			String value = nameValue.getValue().toString();
			//Address addresses = (Address)nameValue.getValue();
			switch (nameValue.getName()) {
				case "name":
					this.name = value;
					break;
				case "phoneNumber":
					this.phoneNumber = value;
					break;
				case "nickName":
					this.nickName = value;
					break;
				case "birthDay":
					this.birthDay = value;
					break;
				case "roles":
					this.roles = Roles.valueOf(nameValue.getValue().toString().substring(5));
					break;
				case "id_token":
					this.idToken = value;
			}
		}
	}

//	public static CommunityMember sample() {
//		//
//		CommunityMember member = new CommunityMember("mymy@nextree.co.kr", "Minsoo Lee", "010-3321-1001", "123", "asdf1234");
//		member.setBirthDay("2001.09.23");
//		return member;
//	}

//	public static void main(String[] args) {
//		//
//		System.out.println(new Gson().toJson(sample()));
//	}

}
