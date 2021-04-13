package com.flaminiovilla.geris.repository;

import com.flaminiovilla.geris.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface RegionRepository extends JpaRepository<Region, Long> {
}
