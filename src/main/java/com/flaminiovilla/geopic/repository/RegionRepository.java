package com.flaminiovilla.geopic.repository;

import com.flaminiovilla.geopic.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface RegionRepository extends JpaRepository<Region, Long> {
}
