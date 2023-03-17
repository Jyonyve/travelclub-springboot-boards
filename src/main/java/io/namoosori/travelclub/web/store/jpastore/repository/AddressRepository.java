package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.service.jpo.AddressJpo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressJpo, String > {
}
