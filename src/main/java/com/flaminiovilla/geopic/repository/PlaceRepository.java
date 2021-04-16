package com.flaminiovilla.geopic.repository;
import com.flaminiovilla.geopic.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByComuneStartsWithOrderByComune(String director);
}
