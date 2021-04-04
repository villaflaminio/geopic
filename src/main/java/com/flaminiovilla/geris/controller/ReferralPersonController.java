package com.flaminiovilla.geris.controller;

import com.flaminiovilla.geris.controller.dto.ReferralPersonDTO;
import com.flaminiovilla.geris.model.ReferralPerson;
import com.flaminiovilla.geris.service.ReferralPersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
