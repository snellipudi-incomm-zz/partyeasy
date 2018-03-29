package com.neotech.partyeasy.services;



import com.neotech.partyeasy.constants.UserEvents;
import com.neotech.partyeasy.model.Address;
import com.neotech.partyeasy.model.PartySpace;
import com.neotech.partyeasy.model.PartySpaces;
import com.neotech.partyeasy.repository.AddressRepository;
import com.neotech.partyeasy.repository.CostRepository;
import com.neotech.partyeasy.repository.PartySpaceRepository;
import com.neotech.partyeasy.response.BaseResponse;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClientService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CostRepository costRepository;

    @Autowired
    PartySpaceRepository partySpaceRepository;

    private XLogger xLogger = XLoggerFactory.getXLogger(getClass());


    public Object postPartySpaceService(PartySpace partySpace) {
        xLogger.entry("Message : Entered postPartySpaceService, UserEvents = ", UserEvents.CLIENTSERVICE);
        xLogger.info("Message : Saving partyspace address details to address table", UserEvents.CLIENTSERVICE);
        addressRepository.save(partySpace.getAddress());
        xLogger.info("Message : Saving partyspace cost details to cost table", UserEvents.CLIENTSERVICE);
        costRepository.save(partySpace.getCost());
        xLogger.info("Message : Saving partyspace  details to party space table", UserEvents.CLIENTSERVICE);
        partySpaceRepository.save(partySpace);

        xLogger.info("Message : Building success response", UserEvents.CLIENTSERVICE);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setResponseCode("200");
        baseResponse.setResponseMessage("Success");
        return baseResponse;


    }

    public Object getPartySpaceService(String email) throws Exception{
        xLogger.entry("Message : Entered getPartySpaceService, UserEvents = ", UserEvents.CLIENTSERVICE);
        PartySpace partySpace = partySpaceRepository.findByEmail(email);
        return partySpace;

    }

    public PartySpaces getPartySpaces(String city) throws Exception{
        xLogger.entry("Message : Entered getAllPartySpaceService, UserEvents = ", UserEvents.CLIENTSERVICE);
        PartySpaces partySpaces = new PartySpaces();
        partySpaces.setPartySpaces(partySpaceRepository.findAllByAddress_City(city));
        return partySpaces;
    }


}
