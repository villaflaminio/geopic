package com.flaminiovilla.geopic.controller;

import com.flaminiovilla.geopic.model.ReferralPerson;
import com.flaminiovilla.geopic.service.ReferralPersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/referralPerson")
public class ReferralPersonController {

    @Autowired
    private ReferralPersonServiceImpl referralPersonService;

    @GetMapping("/findAll")
    List<ReferralPerson> findAll(){
        return referralPersonService.findAll();
    }

}
