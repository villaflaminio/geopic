package com.flaminiovilla.geris.repository;
import com.flaminiovilla.geris.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByComuneStartsWithOrderByComune(String director);
}
