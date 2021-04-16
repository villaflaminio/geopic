package com.flaminiovilla.geopic.repository;

import com.flaminiovilla.geopic.model.Structure;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface StructureRepository extends JpaRepository<Structure, Long> {

    List<Structure> findByMacroCategoryEqualsAndAddressContains(String macroCategory,String location);
    List<Structure> findByMacroCategoryEquals(String macroCategory);

    List<Structure> findByCategoryNameAndAddressContains(String category,String address);

    List<Structure> findByNameContainsAndMacroCategoryEqualsAndAddressContains(String path, String macroCategory,String location);
    List<Structure> findByNameContainsAndMacroCategoryEquals(String path, String macroCategory);
    List<Structure> findByNameContainsAndCategoryNameAndAddressContains(String search, String category, String location );
    List<Structure> findByNameContainsAndCategoryName(String search, String category);

    // citylocation -> List<Structure> findByAddressContains(String address);
    List<Structure> findByCategoryName(String name);
    List<Structure> findByAddressContains(String address);
    List<Structure> findByNameContains(String director);
    @NotNull Optional<Structure> findById(@NotNull Long id);
    List<Structure> findByCategoryNameAndNameContains(String name,String search);
    List<Structure> findByNameContainsAndAddressContains(String search,String location );
    Structure findFirstByLatitudeAndLongitude(Double latitude, Double longitude);

    boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);

}
