package com.flaminiovilla.geopic.repository;

import com.flaminiovilla.geopic.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface NewsRepository extends JpaRepository<News,Long> {
     Optional<List<News>> findBySectionOrderByDateAsc(String section);
     Optional<List<News>>  findByTitleContainsAndSection(String title, String section);
     Optional<List<News>> findByDescriptionContainsAndSection(String description ,String section);
     Boolean existsByTitle(String title);

}
