package com.flaminiovilla.geopic.service;

import com.flaminiovilla.geopic.controller.dto.SecretaryDTO;
import com.flaminiovilla.geopic.model.Secretary;
import com.flaminiovilla.geopic.security.model.User;

import java.util.List;
import java.util.Optional;

public interface SecretaryService {
    List<Secretary> findAll();
    String findAllShort(User user);
    Optional<Secretary> findById(SecretaryDTO secretaryDTO);
    Secretary create(SecretaryDTO secretaryDTO);
    Secretary update(SecretaryDTO secretaryDTO);
    Boolean delete(SecretaryDTO secretaryDTO);
}
