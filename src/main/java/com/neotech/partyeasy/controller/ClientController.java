package com.neotech.partyeasy.controller;


import com.neotech.partyeasy.model.PartySpace;
import com.neotech.partyeasy.model.PartySpaces;
import com.neotech.partyeasy.response.BaseResponse;
import com.neotech.partyeasy.services.ClientService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/partyeasy")
public class ClientController {


    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ClientService clientService;

    @PostMapping("/postPartySpace")
    @ApiResponses(value = {@ApiResponse(code = 401, message = "UNAUTHORIZED", response = BaseResponse.class),
            @ApiResponse(code = 200, message = "OK", response = BaseResponse.class)
    })
    public ResponseEntity<Object> postPartySpace(@RequestBody PartySpace partySpace) throws Exception{
        return new ResponseEntity<>(clientService.postClientService(partySpace), HttpStatus.OK);

    }


    @GetMapping("/getPartySpace")
    @ApiResponses(value = {@ApiResponse(code = 401, message = "UNAUTHORIZED", response = BaseResponse.class),
            @ApiResponse(code = 200, message = "OK", response = PartySpace.class)
    })
    public ResponseEntity<Object> getPostResponse(@RequestParam(required = true) String email) throws Exception{

        return new ResponseEntity<>(clientService.getClientService(email), HttpStatus.OK);


    }

    @GetMapping("/getClients")
    @ApiResponses(value = {@ApiResponse(code = 401, message = "UNAUTHORIZED", response = BaseResponse.class),
            @ApiResponse(code = 200, message = "OK", response = PartySpaces.class)
    })
    public ResponseEntity<Object> getClients(@RequestParam(required = true) String city) throws Exception{
        return new ResponseEntity<>(clientService.getClients(city), HttpStatus.OK);
    }



}
