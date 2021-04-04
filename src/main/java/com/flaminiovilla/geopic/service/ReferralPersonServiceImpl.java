package com.flaminiovilla.geopic.service;

import com.flaminiovilla.geopic.controller.dto.ReferralPersonDTO;
import com.flaminiovilla.geopic.helper.ReferralPersonHelper;
import com.flaminiovilla.geopic.model.ReferralPerson;
import com.flaminiovilla.geopic.repository.RegionRepository;
import com.flaminiovilla.geopic.security.model.User;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReferralPersonServiceImpl implements ReferralPersonService{

    @Autowired
    private ReferralPersonHelper referralPersonHelper;

    @Autowired
    private RegionRepository regionRepository;

    @Override
    public String findAllShort(User user) {
        return referralPersonHelper.findAllShort(user);
    }
    @Override
    public List<ReferralPerson> findAll() {
        return referralPersonHelper.findAll();
    }

    /**
     * devo verificare che referralPersonDTO.secretaryId esista
     */
    @Override
    public ReferralPerson create(ReferralPersonDTO referralPersonDTO) {

        preconditionCreateUpdate(referralPersonDTO);
        return referralPersonHelper.create(referralPersonDTO);
    }

    @Override
    public ReferralPerson update(ReferralPersonDTO referralPersonDTO) {
        Preconditions.checkArgument(!Objects.isNull(referralPersonDTO.id));
        preconditionCreateUpdate(referralPersonDTO);
        return referralPersonHelper.update(referralPersonDTO);
    }

    @Override
    public Optional<ReferralPerson> findById(ReferralPersonDTO referralPersonDTO) {
        Preconditions.checkArgument(!Objects.isNull(referralPersonDTO.id));
        return referralPersonHelper.findById(referralPersonDTO.id);
    }

    @Override
    public Boolean delete(ReferralPersonDTO referralPersonDTO) {
        Preconditions.checkArgument(!Objects.isNull(referralPersonDTO.id));
        Preconditions.checkArgument(!Objects.isNull(referralPersonDTO.user));

        return referralPersonHelper.delete(referralPersonDTO);
    }

    private void preconditionCreateUpdate(ReferralPersonDTO referralPersonDTO){
        Preconditions.checkArgument(!Objects.isNull(referralPersonDTO.email));
        Preconditions.checkArgument(!Objects.isNull(referralPersonDTO.name));
        Preconditions.checkArgument(!Objects.isNull(referralPersonDTO.surname));
        Preconditions.checkArgument(!Objects.isNull(referralPersonDTO.phone));
        Preconditions.checkArgument(!Objects.isNull(referralPersonDTO.secretaryId));
        Preconditions.checkArgument(!Objects.isNull(referralPersonDTO.regionId));
        Preconditions.checkArgument(!Objects.isNull(referralPersonDTO.user));
    }

}
