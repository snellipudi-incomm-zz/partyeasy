package com.neotech.partyeasy.services;


import com.neotech.partyeasy.constants.Constants;
import com.neotech.partyeasy.constants.UserEvents;
import com.neotech.partyeasy.model.PartySpace;
import com.neotech.partyeasy.repository.PartySpaceRepository;
import com.neotech.partyeasy.request.LoginRequest;
import com.neotech.partyeasy.response.BaseResponse;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.MDC;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.neotech.partyeasy.interceptor.MDCInterceptor.SESSION_ID;

@Service
public class AuthenticateService {

    @Autowired
    PartySpaceRepository partySpaceRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    /*@Autowired
    StringEncryptor stringEncryptor;*/

    private XLogger xLogger = XLoggerFactory.getXLogger(getClass());

    String client;

    public Object loginAuthentication(LoginRequest loginRequest) throws Exception{
        xLogger.entry("Message : Entered loginAuthentication method in UserEvents = ", UserEvents.AUTHENTICATESERVICE);
        BaseResponse baseResponse = new BaseResponse();
        PartySpace partySpace =  partySpaceRepository.findByEmail(loginRequest.getEmail());
        client = Constants.PARTYSPACE;
        if(partySpace != null){
            if( partySpace.getPassword().equals(loginRequest.getPassword())){
                xLogger.info("Message : User login Successful UserEvents = ",UserEvents.AUTHENTICATESERVICE);
                addSession(loginRequest);
                baseResponse.setResponseCode("200");
                baseResponse.setResponseMessage("Success");
                return baseResponse;
            }else{
                xLogger.info("Message : email and password didn't match, UserEvents = "+ UserEvents.AUTHENTICATESERVICE);
                baseResponse.setResponseCode("90");
                baseResponse.setResponseMessage("invalid password");
                return baseResponse;
            }
        }else {
            xLogger.info("Message : email is not found in partySpace category, UserEvents = "+ UserEvents.AUTHENTICATESERVICE);
            baseResponse.setResponseCode("98");
            baseResponse.setResponseMessage("no email found");
            return baseResponse;
        }

    }

    public String findClient(String email){
        xLogger.entry("Message : entered findClient service, UserEvents"+ UserEvents.AUTHENTICATESERVICE);
        if(partySpaceRepository.findByEmail(email) != null){
            xLogger.info("Message : email belongs to partyspace client, UserEvents", UserEvents.AUTHENTICATESERVICE);
            return Constants.PARTYSPACE;
        }else{
            xLogger.info("Message : email not found in any clients. UserEvents", UserEvents.AUTHENTICATESERVICE);
            return "not found";
        }
    }



    public void addSession(LoginRequest loginRequest){
        HttpSession newSession = httpServletRequest.getSession(true);
        xLogger.info("Message : Adding email to session, UserEvent = ", UserEvents.AUTHENTICATESERVICE);
        newSession.setAttribute("email", loginRequest.getEmail());
        newSession.setAttribute(Constants.CLIENT, client);
        xLogger.info("Message = Adding the session traceid to the session which is used to trace the calls after the login, UserEvents", UserEvents.AUTHENTICATESERVICE);
        newSession.setAttribute("session-TraceId", MDC.get(SESSION_ID));
    }

}
