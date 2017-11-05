package com.neotech.crossfit.controller;

import com.neotech.crossfit.constants.Constants;
import com.neotech.crossfit.constants.UserEvents;
import com.neotech.crossfit.response.BaseResponse;
import com.neotech.crossfit.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/crossfit")
public class UserController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/getUserDetails", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserDetails(@RequestHeader(value = "x-auth-token") String sessionId, HttpServletRequest request) throws UnknownHostException {

        logger.info("message = transfer funds endpoint. Retrieving the session id, userEvent" + UserEvents.GETUSERDETAILS);
        HttpSession newSession = httpServletRequest.getSession(false);
        logger.info("message = validating the session, userEvent" + UserEvents.GETUSERDETAILS );

        if(newSession != null){
            try {
                LoginResponse loginResponse = new LoginResponse();
                logger.info("message = Retrieving values from session, userEvent=" + UserEvents.GETUSERDETAILS);
                loginResponse.setUserName(newSession.getAttribute("userName").toString());
                loginResponse.setAge(newSession.getAttribute("age").toString());
                loginResponse.setType(newSession.getAttribute("type").toString());
                loginResponse.setSex(newSession.getAttribute("sex").toString());
                return new ResponseEntity<>(loginResponse, HttpStatus.OK);
            } catch (javax.xml.ws.WebServiceException e) {
                logger.error("message = WebServiceException Occurred while calling CHS, userEvent=" + UserEvents.GETUSERDETAILS);
                logger.error("message = "+" error " + e + ", userEvent " + UserEvents.GETUSERDETAILS);
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResponseCode(Constants.WEBSERVICE_EXCEPTION_CODE);
                baseResponse.setResponseMessage(Constants.ERR_SESSION_EXPIRED_MESSAGE);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);

            } /*catch (IOException e) {
                logger.error("message = IO Exception Occurred while calling CHS, userEvent=" + UserEvents.GETUSERDETAILS);
                logger.error("message =  " + " error " + e + ", userEvent " + UserEvents.GETUSERDETAILS);
                return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
            } */catch (Exception e) {
                logger.error("message = Unexpected Exception Occurred while calling CHS, userEvent=" + UserEvents.GETUSERDETAILS);
                logger.error("message = " + " error " + e + ", userEvent " + UserEvents.GETUSERDETAILS);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            logger.error("message = Session expired, userEvent=" + UserEvents.GETUSERDETAILS);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setResponseCode(Constants.ERR_SESSION_EXPIRED_CODE);
            baseResponse.setResponseMessage(Constants.ERR_SESSION_EXPIRED_MESSAGE);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(baseResponse);
        }

    }
}
