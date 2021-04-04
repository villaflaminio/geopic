package com.flaminiovilla.geopic.service;

import com.flaminiovilla.geopic.controller.dto.SecretaryDTO;
import com.flaminiovilla.geopic.helper.SecretaryHelper;
import com.flaminiovilla.geopic.model.Secretary;
import com.flaminiovilla.geopic.security.model.User;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SecretaryServiceImpl implements SecretaryService{

    @Autowired
    private SecretaryHelper secretaryHelper;

    @Override
    public List<Secretary> findAll() {
        return secretaryHelper.findAll();
    }
    @Override
    public String findAllShort(User user) {
        return secretaryHelper.findAllShort(user);
    }

    @Override
    public Optional<Secretary> findById(SecretaryDTO secretaryDTO) {
        return secretaryHelper.findById(secretaryDTO.id);
    }

    //non possono esistere due secretary con lo stesso nome
    @Override
    public Secretary create(SecretaryDTO secretaryDTO) {
        preconditionCreateUpdate(secretaryDTO);

        return secretaryHelper.create(secretaryDTO);

    }
    // come nel create pero' bisogna controllare anche l'id
    @Override
    public Secretary update(SecretaryDTO secretaryDTO) {
        Preconditions.checkArgument(!Objects.isNull(secretaryDTO.id));
        preconditionCreateUpdate(secretaryDTO);
        return secretaryHelper.update(secretaryDTO);
    }

    @Override
    public Boolean delete(SecretaryDTO secretaryDTO) {
        Preconditions.checkArgument(!Objects.isNull(secretaryDTO.id));
        Preconditions.checkArgument(!Objects.isNull(secretaryDTO.user));

        return secretaryHelper.delete(secretaryDTO);
    }

    private void preconditionCreateUpdate(SecretaryDTO secretaryDTO){
        Preconditions.checkArgument(!Objects.isNull(secretaryDTO.name));
        Preconditions.checkArgument(!Objects.isNull(secretaryDTO.address));
        Preconditions.checkArgument(!Objects.isNull(secretaryDTO.email));
        Preconditions.checkArgument(!Objects.isNull(secretaryDTO.phone));
        Preconditions.checkArgument(!Objects.isNull(secretaryDTO.regionId));
        Preconditions.checkArgument(!Objects.isNull(secretaryDTO.user));
    }
}
