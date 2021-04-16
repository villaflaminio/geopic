package com.flaminiovilla.geopic.service;

import com.flaminiovilla.geopic.component.Scraper;
import com.flaminiovilla.geopic.controller.dto.NewsDTO;
import com.flaminiovilla.geopic.controller.dto.StructureDTO;
import com.flaminiovilla.geopic.helper.NewsHelper;
import com.flaminiovilla.geopic.model.News;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsHelper newsHelper;
    @Autowired
    Scraper scraper;

    @Override
    public List<NewsDTO> search(String search, StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.section));
        Preconditions.checkArgument(!Objects.isNull(search));
        return newsHelper.search(search, structureDTO);
    }

    @Override
    public List<NewsDTO> findAllNewsBySection(StructureDTO structureDTO) {
        Preconditions.checkArgument(!Objects.isNull(structureDTO.section));
        return newsHelper.findAllNewsBySection(structureDTO);
    }

    @Override
    public News create(NewsDTO newsDTO) {
        if(preconditionCreateUpdate(newsDTO))
            return newsHelper.create(newsDTO);
        return null;
    }
    public void scraper() {
       scraper.scraping();
    }

    @Override
    public News update(NewsDTO newsDTO) {
        Preconditions.checkArgument(!Objects.isNull(newsDTO.id));
        if(preconditionCreateUpdate(newsDTO))
            return newsHelper.update(newsDTO);
        return null;
    }

    @Override
    public Optional<News> findById(NewsDTO newsDTO) {
        Preconditions.checkArgument(!Objects.isNull(newsDTO.id));

        return newsHelper.findById(newsDTO);
    }

    @Override
    public Boolean delete(NewsDTO newsDTO) {
        Preconditions.checkArgument(!Objects.isNull(newsDTO.id));
        return newsHelper.delete(newsDTO.id);
    }

    private boolean preconditionCreateUpdate(NewsDTO newsDTO) {
        Preconditions.checkArgument(!Objects.isNull(newsDTO.description));
        Preconditions.checkArgument(!Objects.isNull(newsDTO.title));
        Preconditions.checkArgument(!Objects.isNull(newsDTO.image));
        Preconditions.checkArgument(!Objects.isNull(newsDTO.date));
        Preconditions.checkArgument(!Objects.isNull(newsDTO.section));
        if(newsDTO.section.equals("News") || newsDTO.section.equals("Comunicazioni") || newsDTO.section.equals("Eventi"))
            return true;
        return false;
    }

}
