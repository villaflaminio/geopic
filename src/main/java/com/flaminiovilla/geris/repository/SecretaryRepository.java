package com.flaminiovilla.geris.repository;

import com.flaminiovilla.geris.model.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface SecretaryRepository extends JpaRepository<Secretary, Long> {
    List<Secretary> findAllByOrderByIdAsc();
    boolean existsByName(String name);

    Secretary getById(Long id);

}
