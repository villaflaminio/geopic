package com.flaminiovilla.geris.helper;

import com.flaminiovilla.geris.controller.dto.NewsDTO;
import com.flaminiovilla.geris.controller.dto.StructureDTO;
import com.flaminiovilla.geris.exception.NewsException;
import com.flaminiovilla.geris.model.News;
import com.flaminiovilla.geris.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

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
        throw new NewsException(NewsException.newsExceptionCode.CREATING_EXCEPTION);
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
                throw new NewsException(NewsException.newsExceptionCode.NEWS_DELETE_ERROR);
            }
        }
        throw new NewsException(NewsException.newsExceptionCode.NEWS_ID_NOT_EXIST);

    }
}