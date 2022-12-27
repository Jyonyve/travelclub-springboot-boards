package io.namoosori.travelclub.web.store;

import io.namoosori.travelclub.web.aggregate.club.Address;

import java.util.List;

public interface AddressStore {
    String create(Address address);
    Address retrieve(String addressId);
    //Address retrieveByMemberId(String memberId);
    List<Address> retrieveAll();
    void update(Address address);
    void delete(String memberId);
    boolean exists(String memberId);
}
