package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.service.jpo.TestBoardJpo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestBoardRepository extends JpaRepository<TestBoardJpo, String> {
}
