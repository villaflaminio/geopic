package com.flaminiovilla.geris.service;

import com.flaminiovilla.geris.controller.dto.ReferralPersonDTO;
import com.flaminiovilla.geris.model.ReferralPerson;
import com.flaminiovilla.geris.security.model.User;

import java.util.List;
import java.util.Optional;

public interface ReferralPersonService {
    List<ReferralPerson> findAll();
    ReferralPerson create(ReferralPersonDTO referralPersonDTO);
    ReferralPerson update(ReferralPersonDTO referralPersonDTO);
    Optional<ReferralPerson> findById(ReferralPersonDTO referralPersonDTO);
    Boolean delete(ReferralPersonDTO referralPersonDTO);
    String findAllShort(User user);
}
