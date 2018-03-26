package com.neotech.partyeasy.repository;

import com.neotech.partyeasy.model.PartySpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartySpaceRepository extends JpaRepository<PartySpace, Long> {

    PartySpace findByEmail(String email);
}
