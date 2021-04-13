package com.flaminiovilla.geris.service;

import com.flaminiovilla.geris.controller.dto.SecretaryDTO;
import com.flaminiovilla.geris.model.Secretary;
import com.flaminiovilla.geris.security.model.User;

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
