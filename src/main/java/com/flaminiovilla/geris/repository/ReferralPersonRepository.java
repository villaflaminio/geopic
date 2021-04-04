package com.flaminiovilla.geris.repository;

import com.flaminiovilla.geris.model.ReferralPerson;
import com.flaminiovilla.geris.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ReferralPersonRepository extends JpaRepository<ReferralPerson, Long> {
    List<ReferralPerson> findAllByOrderByIdAsc();
    List<ReferralPerson> findAllByRegionOrderByIdAsc(Region region);

    List<ReferralPerson> findByIdAndRegion (Long id, Region region);

    Boolean existsByEmail(String email);

}
