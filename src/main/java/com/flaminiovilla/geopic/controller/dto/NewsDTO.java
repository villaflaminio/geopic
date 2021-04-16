package com.flaminiovilla.geopic.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flaminiovilla.geopic.model.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDTO {
    public Long id;
    @Lob
    public String description;
    public String title;
    public String image;
    public String date;
    public String section;

    public NewsDTO(News n) {
        this.id = n.getId();
        this.title = n.getTitle();
        this.image = n.getImage();
        this.description = n.getDescription();
        this.section = n.getSection();
        this.date = n.getDate();
    }


    public static List<NewsDTO> listNewsToDTO(List<News> newsList) {
        List<NewsDTO> newsDTOS = new ArrayList<>();

        for (News n : newsList) {
            System.out.println(n);
            newsDTOS.add(new NewsDTO(n));
        }
        return newsDTOS;
    }
}
