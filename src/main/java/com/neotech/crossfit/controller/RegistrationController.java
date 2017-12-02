package com.neotech.crossfit.controller;

import com.neotech.crossfit.constants.Constants;
import com.neotech.crossfit.constants.UserEvents;
import com.neotech.crossfit.request.RegistrationRequest;
import com.neotech.crossfit.response.BaseResponse;
import com.neotech.crossfit.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/crossfit")
public class RegistrationController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<Object> registration(@RequestBody RegistrationRequest registrationRequest, HttpServletRequest request) throws UnknownHostException {

        logger.info("message = entered into login call, userEvent" + UserEvents.REGISTRATION);
            try {
                logger.info("message = sending the data to the DB, userEvent=" + UserEvents.REGISTRATION);
                BaseResponse baseResponse = new BaseResponse();

                if(!registrationRequest.getUserName().isEmpty() && !registrationRequest.getPassword().isEmpty()){
                    //pass data to the service which has the code of db
                    baseResponse.setResponseCode(Constants.SUCCESS_CODE);
                    baseResponse.setResponseMessage(Constants.SUCCESS_MESSAGE);
                    return new ResponseEntity<>(baseResponse, HttpStatus.OK);

                } else{
                    baseResponse.setResponseCode(Constants.WEBSERVICE_EXCEPTION_CODE);
                    baseResponse.setResponseMessage(Constants.WEBSERVICE_EXCEPTION_MESSAGE);
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(baseResponse);

                }


            } catch (javax.xml.ws.WebServiceException e) {
                logger.error("message = WebServiceException Occurred while calling CHS, userEvent=" + UserEvents.GETUSERDETAILS);
                logger.error("message = "+" error " + e + ", userEvent " + UserEvents.GETUSERDETAILS);
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setResponseCode(Constants.WEBSERVICE_EXCEPTION_CODE);
                baseResponse.setResponseMessage(Constants.ERR_SESSION_EXPIRED_MESSAGE);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);

            } catch (NullPointerException e) {
                logger.error("message =  Exception Occurred while calling CHS, userEvent=" + UserEvents.GETUSERDETAILS);
                logger.error("message =  " + " error " + e + ", userEvent " + UserEvents.GETUSERDETAILS);
                return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
            } catch (Exception e) {
                logger.error("message = Unexpected Exception Occurred while calling CHS, userEvent=" + UserEvents.GETUSERDETAILS);
                logger.error("message = " + " error " + e + ", userEvent " + UserEvents.GETUSERDETAILS);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }


    }


}
