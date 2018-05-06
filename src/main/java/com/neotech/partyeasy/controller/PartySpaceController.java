package com.neotech.partyeasy.controller;


import com.neotech.partyeasy.constants.Constants;
import com.neotech.partyeasy.constants.UserEvents;
import com.neotech.partyeasy.model.PartySpace;
import com.neotech.partyeasy.model.PartySpaces;
import com.neotech.partyeasy.repository.PartySpaceRepository;
import com.neotech.partyeasy.response.BaseResponse;
import com.neotech.partyeasy.services.ClientService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/partyeasy")
public class PartySpaceController {


    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ClientService clientService;

    @Autowired
    PartySpaceRepository partySpaceRepository;

    private XLogger xLogger = XLoggerFactory.getXLogger(getClass());

    @PostMapping("/postPartySpace")
    @ApiResponses(value = {@ApiResponse(code = 401, message = "UNAUTHORIZED", response = BaseResponse.class),
            @ApiResponse(code = 200, message = "OK", response = BaseResponse.class)
    })
    public ResponseEntity<Object> postPartySpace(@RequestBody PartySpace partySpace) throws Exception{
        return new ResponseEntity<>(clientService.postPartySpaceService(partySpace), HttpStatus.OK);

    }


    @GetMapping("/getPartySpace")
    @ApiResponses(value = {@ApiResponse(code = 401, message = "UNAUTHORIZED", response = BaseResponse.class),
            @ApiResponse(code = 200, message = "OK", response = PartySpace.class)
    })
    public ResponseEntity<Object> getPostResponse(@RequestParam(required = true) String email) throws Exception{

        return new ResponseEntity<>(clientService.getPartySpaceService(email), HttpStatus.OK);


    }

    @GetMapping("/getAllPartySpaces")
    @ApiResponses(value = {@ApiResponse(code = 401, message = "UNAUTHORIZED", response = BaseResponse.class),
            @ApiResponse(code = 200, message = "OK", response = PartySpaces.class)
    })
    public ResponseEntity<Object> getClients(@RequestParam(required = true) String city) throws Exception{
        return new ResponseEntity<>(clientService.getPartySpaces(city), HttpStatus.OK);
    }

    @PostMapping("/updatePartySpace")
    @ApiResponses(value = {@ApiResponse(code = 401, message = "UNAUTHORIZED", response = BaseResponse.class),
            @ApiResponse(code = 200, message = "OK", response = BaseResponse.class)
    })
    public ResponseEntity<Object> updatePartySpace(@RequestHeader(value = "x-auth-token") String sessionId, @RequestBody PartySpace partySpace, HttpServletRequest request) {
        HttpSession session = httpServletRequest.getSession(false);

        xLogger.info("Message : validating session, UserEvents", UserEvents.AUTHENTICATECONTROLLER);
        if(session != null){
            if(session.getAttribute("email").equals(partySpace.getEmail())){
                if(session.getAttribute(Constants.CLIENT).equals(Constants.PARTYSPACE)){
                    xLogger.info("Message : adding partyspace_id to the partyspace request, UserEvents = ", UserEvents.UPDATEPARTYSPACECLIENT );
                    partySpace.setParty_space_id(partySpaceRepository.findByEmail(partySpace.getEmail()).getParty_space_id());
                    return new ResponseEntity<>(clientService.postPartySpaceService(partySpace), HttpStatus.OK);

                }
                else {
                    BaseResponse baseResponse = new BaseResponse();
                    baseResponse.setResponseCode("91");
                    baseResponse.setResponseMessage("Invalid email");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
                }
            }else {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResponseCode("91");
                baseResponse.setResponseMessage("Not Authorized");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(baseResponse);
            }

        }
            else{
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setResponseCode("91");
            baseResponse.setResponseMessage("Invalid Details");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(baseResponse);
            }


    }

}
