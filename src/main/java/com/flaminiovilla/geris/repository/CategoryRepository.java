package com.flaminiovilla.geris.repository;

import com.flaminiovilla.geris.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface CategoryRepository extends JpaRepository<Category,Long> {
     List<Category> findAllByOrderByIdAsc();
     Boolean existsByNameOrColorEquals(String name , String color);


}
