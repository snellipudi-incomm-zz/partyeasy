package com.neotech.partyeasy.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.neotech.partyeasy.model.Address;
import com.neotech.partyeasy.model.Cost;
import com.neotech.partyeasy.model.PartySpace;
import com.neotech.partyeasy.repository.AddressRepository;
import com.neotech.partyeasy.repository.CostRepository;
import com.neotech.partyeasy.repository.PartySpaceRepository;
import com.neotech.partyeasy.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CostRepository costRepository;

    @Autowired
    PartySpaceRepository partySpaceRepository;

    public Object postClientService(PartySpace partySpace) throws Exception{

        addressRepository.save(partySpace.getAddress());
        costRepository.save(partySpace.getCost());
        partySpaceRepository.save(partySpace);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResponseCode("200");
        baseResponse.setResponseMessage("Success");

        return baseResponse;


    }

    public Object getClientService(String email) throws Exception{

        //ObjectMapper mapper = new ObjectMapper();

        //PartySpace partySpace = partySpaceRepository.findByEmail(email);

        PartySpace partySpace = mapper.readValue(partySpaceRepository.findByEmail(email),PartySpace.class);
        return partySpace;

    }

    public List<PartySpace> getClients(String city) throws Exception{

        Address address = new Address("530026", "27-3-112", "official colony","Srinagar, Gajuwaka", "Visakhapatnam", "9966123456", "9499123456");
        Cost cost = new Cost(12000.00, 15000.00);
        List<String> holidayDates = new ArrayList<>();
        holidayDates.add("25-08-2018");
        holidayDates.add("27-09-2018");
        PartySpace partySpace = new PartySpace("Hotel Sitara", "Subba Rao", address, 100, cost, "manager@sitara.com","sitara", "This is one of the major hotels in the north of Hyderabad", holidayDates );
        PartySpace partySpace1 = new PartySpace("Hotel grandBay", "Rama Rao", address, 200, cost, "Srmanager@sitara.com","grand", "This is one of the major hotels in the of Hyderabad", holidayDates );
        List<PartySpace> partySpaceList = new ArrayList<>();
        partySpaceList.add(partySpace);
        partySpaceList.add(partySpace1);
        return partySpaceList;
    }
}
