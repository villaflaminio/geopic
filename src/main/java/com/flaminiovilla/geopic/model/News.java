package com.flaminiovilla.geopic.model;

import com.flaminiovilla.geopic.controller.dto.NewsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Lob
    private String description;
    private String title;

    private String image;
    private String date;
    private String section;


    public News(Long id , NewsDTO n) {
        this.id = id;
        this.description = n.getDescription();
        this.title = n.getTitle();
        this.image = n.getImage();
        this.date =  n.getDate();
        this.section = n.getSection();
    }


}
