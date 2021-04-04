package com.flaminiovilla.geris.helper;

import com.flaminiovilla.geris.component.Utils;
import com.flaminiovilla.geris.controller.dto.NewsDTO;
import com.flaminiovilla.geris.controller.dto.StructureDTO;
import com.flaminiovilla.geris.exception.NewsException;
import com.flaminiovilla.geris.model.News;
import com.flaminiovilla.geris.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

import static com.flaminiovilla.geris.exception.NewsException.newsExceptionCode.*;

@Component
public class NewsHelper {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public List<NewsDTO> search(String search, StructureDTO structureDTO) {
        Optional<List<News>> newsList = newsRepository.findByTitleContainsAndSection(search , structureDTO.section);
        if(newsList.isEmpty())
            newsList = newsRepository.findByDescriptionContainsAndSection(search , structureDTO.section);

        if(newsList.isEmpty())
            return null;

        return NewsDTO.listNewsToDTO(newsList.get());
    }

    public List<NewsDTO> findAllNewsBySection(StructureDTO structureDTO) {
        Optional<List<News>> newsList = newsRepository.findBySectionOrderByDateAsc(structureDTO.section);
        if(newsList.isEmpty())
            return null;

        return NewsDTO.listNewsToDTO(newsList.get());

    }

    public News create(NewsDTO newsDTO) {
        if (!newsRepository.existsByTitle(newsDTO.title)) {
            return newsRepository.save(News.builder()
                    .description(newsDTO.description)
                    .title(newsDTO.title)
                    .image(newsDTO.image)
                    .section(newsDTO.section)
                    .date(newsDTO.date)
                    .build());
        }
        throw new NewsException(CREATING_EXCEPTION);
    }

    public News update(NewsDTO newsDTO) {
        return newsRepository.save(News.builder()
                .id(newsDTO.id)
                .description(newsDTO.description)
                .title(newsDTO.title)
                .description(newsDTO.description)
                .image(newsDTO.image)
                .section(newsDTO.section)
                .date(newsDTO.date)
                .build());
    }

    public Optional<News> findById(NewsDTO newsDTO) {
        return newsRepository.findById(newsDTO.id);
    }

    public Boolean delete(Long id) {
        if(newsRepository.existsById(id)) {
            try {
                newsRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                throw new NewsException(NEWS_DELETE_ERROR);
            }
        }
        throw new NewsException(NEWS_ID_NOT_EXIST);

    }
}