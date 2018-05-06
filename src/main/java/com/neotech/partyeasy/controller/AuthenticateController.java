package com.neotech.partyeasy.controller;

import com.neotech.partyeasy.constants.Constants;
import com.neotech.partyeasy.constants.UserEvents;
import com.neotech.partyeasy.model.PartySpace;
import com.neotech.partyeasy.request.LoginRequest;
import com.neotech.partyeasy.response.BaseResponse;
import com.neotech.partyeasy.services.AuthenticateService;
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
public class AuthenticateController {


    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    AuthenticateService authenticateService;

    @Autowired
    ClientService clientService;

    private XLogger xLogger = XLoggerFactory.getXLogger(getClass());

    @PostMapping("/loginAuthenticate")
    @ApiResponses(value = {@ApiResponse(code = 401, message = "UNAUTHORIZED", response = BaseResponse.class),
            @ApiResponse(code = 200, message = "OK", response = BaseResponse.class)
    })
    public ResponseEntity<Object> authenticate(@RequestBody LoginRequest loginRequest) throws Exception{
        return new ResponseEntity<>(authenticateService.loginAuthentication(loginRequest), HttpStatus.OK);
    }

   /* @PostMapping("/updateClient")
    @ApiResponses(value = {@ApiResponse(code = 401, message = "UNAUTHORIZED", response = BaseResponse.class),
            @ApiResponse(code = 200, message = "OK", response = BaseResponse.class)
    })
    public ResponseEntity<Object> updateClient(@RequestHeader(value = "x-auth-token") String sessionId, @RequestBody PartySpace partySpace, HttpServletRequest request) {
        HttpSession session = httpServletRequest.getSession(false);
        String client;
        xLogger.info("Message : validating session, UserEvents", UserEvents.AUTHENTICATECONTROLLER);
        if(session != null){
           client  = authenticateService.findClient(session.getAttribute("email").toString());
           switch (client){
               case Constants.PARTYSPACE:
                   partySpace.setEmail(session.getAttribute("email").toString());
                   return new ResponseEntity<>(clientService.postPartySpaceService(partySpace), HttpStatus.OK);

               default:
                   BaseResponse baseResponse = new BaseResponse();
                   baseResponse.setResponseCode("91");
                   baseResponse.setResponseMessage("Invalid Details");
                   return ResponseEntity.status(HttpStatus.NO_CONTENT).body(baseResponse);

           }
        } else{
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setResponseCode("89");
            baseResponse.setResponseMessage("Invalid Details");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(baseResponse);
        }



    }*/
}
