package io.namoosori.travelclub.web.aggregate.club;

import io.namoosori.travelclub.web.aggregate.Entity;
import io.namoosori.travelclub.web.aggregate.club.vo.AddressType;
import io.namoosori.travelclub.web.shared.NameValue;
import io.namoosori.travelclub.web.shared.NameValueList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@Setter
@RequiredArgsConstructor
public class Address extends Entity {
	//
	@Id
	private String id;
	private String zipCode;
	private String zipAddress;
	private String streetAddress;
	private String country;
	@Enumerated(EnumType.STRING)
	private AddressType addressType;
	private String memberId;



	public Address(
			String zipCode,
			String zipAddress,
			String streetAddress,
			String country,
			AddressType addressType,
			String memberId) {
		this.zipCode = zipCode;
		this.zipAddress = zipAddress;
		this.streetAddress = streetAddress;
		this.country = country;
		this.addressType = addressType;
		this.memberId = memberId;

	}

	public void modifyValues(NameValueList nameValues) {
		//
		for (NameValue nameValue : nameValues.getNameValues()) {
			String value = String.valueOf(nameValue.getValue());
			switch (nameValue.getName()) {
				case "zipCode":
					this.zipCode = value;
					break;
				case "zipAddress":
					this.zipAddress = value;
					break;
				case "streetAddress":
					this.streetAddress = value;
					break;
				case "country":
					this.country = value;
					break;
				case "addressType":
					this.addressType = AddressType.valueOf(value);
					break;

			}
		}
	}

	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder();
		
		builder.append("ZipCode:").append(zipCode); 
		builder.append("/zip address:").append(zipAddress);
		builder.append("/street address:").append(streetAddress);
		builder.append("/country:").append(country);
		builder.append("/address type:").append(addressType);
		builder.append("/");
	
		return builder.toString(); 
	}

}