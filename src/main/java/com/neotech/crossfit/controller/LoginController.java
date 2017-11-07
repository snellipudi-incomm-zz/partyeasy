package com.neotech.crossfit.controller;


import com.neotech.crossfit.constants.Constants;
import com.neotech.crossfit.constants.UserEvents;
import com.neotech.crossfit.request.LoginRequest;
import com.neotech.crossfit.response.BaseResponse;
import com.neotech.crossfit.response.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.neotech.crossfit.logging.MDCInterceptor.SESSION_ID;

@RestController
@RequestMapping("/crossfit")
public class LoginController {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    HttpServletRequest httpServletRequest;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginAuthenticate(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest){

        BaseResponse baseResponse = new BaseResponse();
        try{

            if(loginRequest.getUserName().equals("test01") && loginRequest.getPassword().equals("Demo1234")){
                baseResponse.setResponseCode(Constants.SUCCESS_CODE);
                baseResponse.setResponseMessage(Constants.SUCCESS_MESSAGE);
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setUserName("test01");
                loginResponse.setSex("Male");
                loginResponse.setAge(25);
                loginResponse.setType("premium");
                addSession(loginResponse );
                return new ResponseEntity<>(baseResponse, HttpStatus.OK);
            } else{
                baseResponse.setResponseCode(Constants.UNAUTHORIZED_CODE);
                baseResponse.setResponseMessage(Constants.UNAUTHORIZED_MESSAGE);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(baseResponse);
            }

        } catch (javax.xml.ws.WebServiceException e) {
            logger.error("message = WebServiceException Occurred while calling CHS, userEvent=" + UserEvents.LOGIN);
            logger.error("message = "+" error " + e + ", userEvent " + UserEvents.LOGIN);
            //BaseResponse baseResponse = new BaseResponse();
            baseResponse.setResponseCode(Constants.WEBSERVICE_EXCEPTION_CODE);
            baseResponse.setResponseMessage(Constants.ERR_SESSION_EXPIRED_MESSAGE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);

        } catch (Exception e){
            baseResponse.setResponseCode(Constants.EXCEPTION_CODE);
            baseResponse.setResponseMessage(Constants.EXCEPTION_MESSAGE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);

        }

    }

    public void addSession(LoginResponse loginResponse){

        HttpSession newSession = httpServletRequest.getSession(true);
        newSession.setAttribute("userName", loginResponse.getUserName());
        newSession.setAttribute("age", loginResponse.getAge());
        newSession.setAttribute("type", loginResponse.getType());
        newSession.setAttribute("sex", loginResponse.getSex());
        newSession.setAttribute("session-TraceId", MDC.get(SESSION_ID));


    }
}
