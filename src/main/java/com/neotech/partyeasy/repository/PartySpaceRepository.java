package com.neotech.partyeasy.repository;

import com.neotech.partyeasy.model.PartySpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartySpaceRepository extends JpaRepository<PartySpace, Long> {

    PartySpace findByEmail(String email);
    List<PartySpace> findAllByAddress_City(String city);
}
