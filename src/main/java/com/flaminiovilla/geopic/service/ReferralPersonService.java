package com.flaminiovilla.geopic.service;

import com.flaminiovilla.geopic.controller.dto.ReferralPersonDTO;
import com.flaminiovilla.geopic.model.ReferralPerson;
import com.flaminiovilla.geopic.security.model.User;

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
