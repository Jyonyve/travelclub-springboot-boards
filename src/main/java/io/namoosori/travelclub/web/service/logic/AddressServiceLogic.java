package io.namoosori.travelclub.web.service.logic;

import io.namoosori.travelclub.web.aggregate.club.Address;
import io.namoosori.travelclub.web.service.AddressService;
import io.namoosori.travelclub.web.service.sdo.AddressCdo;
import io.namoosori.travelclub.web.shared.NameValueList;
import io.namoosori.travelclub.web.store.AddressStore;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressServiceLogic implements AddressService {

    private AddressStore addressStore;

    public AddressServiceLogic(AddressStore addressStore){
        this.addressStore = addressStore;
    }

    @Override
    public String register(String memberId, AddressCdo addressCdo) {

        Address address = new Address(
                addressCdo.getZipCode(),
                addressCdo.getZipAddress(),
                addressCdo.getStreetAddress(),
                addressCdo.getCountry(),
                addressCdo.getAddressType(),
                memberId
                );

        return addressStore.create(address);
    }

    @Override
    public Address findById(String Id) {
        Address address = addressStore.retrieve(Id);
        if(address == null){
            throw new Error("there are no address with that id");
        }
        return address;
    }

//    @Override
//    public Address findByMemberId(String memberId) {
//        Address address = addressStore.retrieveByMemberId(memberId);
//        return address;
//    }

    @Override
    public List<Address> findAll() {
        List<Address> addressList = addressStore.retrieveAll();
        return addressList;
    }

    @Override
    public void modifyAddress(String addressId, NameValueList address) {
        Address address1 = addressStore.retrieve(addressId);
        if(address1 == null) {
            throw new Error("there are no address with that id");
        }
        address1.modifyValues(address);
        addressStore.update(address1);
    }

    @Override
    public void removeAddress(String addressId) {
        if(!addressStore.exists(addressId)){
            throw new Error("no such address");
        }
        addressStore.delete(addressId);
    }
}
