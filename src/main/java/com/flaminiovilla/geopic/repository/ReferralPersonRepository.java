package com.flaminiovilla.geopic.repository;

import com.flaminiovilla.geopic.model.ReferralPerson;
import com.flaminiovilla.geopic.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ReferralPersonRepository extends JpaRepository<ReferralPerson, Long> {
    List<ReferralPerson> findAllByOrderByIdAsc();
    List<ReferralPerson> findAllByRegionOrderByIdAsc(Region region);

    List<ReferralPerson> findByIdAndRegion (Long id, Region region);

    Boolean existsByEmail(String email);

}
