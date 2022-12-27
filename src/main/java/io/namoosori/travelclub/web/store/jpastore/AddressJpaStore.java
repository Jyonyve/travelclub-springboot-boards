package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.club.Address;
import io.namoosori.travelclub.web.store.AddressStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.AddressJpo;
import io.namoosori.travelclub.web.store.jpastore.repository.AddressRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AddressJpaStore implements AddressStore {

    private AddressRepository addressRepository;
    public AddressJpaStore(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }


    public String create(Address address) {
        AddressJpo addressJpo = new AddressJpo(address);
        addressJpo.setId(address.getId());
        addressRepository.save(addressJpo);
        return addressJpo.getId();
    }

    public Address retrieve(String memberId) {
        Optional<AddressJpo> addressJpo = addressRepository.findById(memberId);
        return addressJpo.get().toDomain();
    }

    //@Override
//    public Address retrieveByMemberId(String memberId) {
//        Optional<Address> address = addressRepository.findByMemberId(memberId);
//        return address.orElseThrow();
//    }

    @Override
    public List<Address> retrieveAll() {
        List<AddressJpo> addressJpos = addressRepository.findAll();
        return addressJpos.stream().map(AddressJpo::toDomain).collect(Collectors.toList());
    }

    public void update(Address address) {
        addressRepository.save(new AddressJpo(address));
    }

    public void delete(String memberId) {
        addressRepository.deleteById(memberId);
    }

    public boolean exists(String memberId) {
        return addressRepository.existsById(memberId);
    }
}
