package io.namoosori.travelclub.web.aggregate.club;

import io.namoosori.travelclub.web.aggregate.club.vo.AddressType;
import io.namoosori.travelclub.web.store.jpastore.jpo.MemberJpo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Address implements Serializable {
	//
	private String zipCode;
	private String zipAddress;
	private String streetAddress;
	private String country;
	@Enumerated(EnumType.STRING)
	private AddressType addressType;
	@OneToOne @MapsId @Id
	@JoinColumn(name="id")
	private MemberJpo memberJpo;

//	public Address(String zipCode, String zipAddress, String streetAddress) {
//		//
//		this.zipCode = zipCode;
//		this.zipAddress = zipAddress;
//		this.streetAddress = streetAddress;
//		this.country = "South Korea";
//		this.addressType = AddressType.Office;
//	}
//
//	public Address(String zipCode, String zipAddress, String streetAddress, String country, AddressType addressType) {
//		this.zipCode = zipCode;
//		this.zipAddress = zipAddress;
//		this.streetAddress = streetAddress;
//		this.country = country;
//		this.addressType = addressType;
//	}

		public Address(String id) {
			//
			this.memberJpo.setId(id);
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

//	public static Address sampleHomeAddress() {
//		//
//		Address address = new Address("134-321", "Seoul, Geumcheon-gu, Gasan-dong", "231");
//		address.setAddressType(AddressType.Home);
//
//		return address;
//	}
//
//	public static Address sampleOfficeAddress() {
//		//
//		Address address = new Address("131-111", "Seoul, Guro-gu, ilsan-dong", "223-201");
//
//		return address;
//	}
//
//	public static void main(String[] args) {
//		//
//		System.out.println(sampleHomeAddress().toString());
//	}
}