package io.namoosori.travelclub.web.service;

import io.namoosori.travelclub.web.aggregate.club.Address;
import io.namoosori.travelclub.web.service.sdo.AddressCdo;
import io.namoosori.travelclub.web.shared.NameValueList;

import java.util.List;

public interface AddressService {
    String register(String MemberId, AddressCdo addressCdo);
    Address findById(String addressId);
//    Address findByMemberId(String memberId);
    List<Address> findAll();
    void modifyAddress(String addressId, NameValueList address);
    void removeAddress(String addressId);
}
