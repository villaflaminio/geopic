package com.flaminiovilla.geris.component;

import com.flaminiovilla.geris.model.News;
import com.flaminiovilla.geris.repository.NewsRepository;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * dato un sito web realizzato in Wordpress
 * analizzo i div che mi interessano per popolare la mia sezione
 * NEWS , quando ho tutti i valori interessati salvo il nuovo obj
 * tramite newsRepository
 */
@Transactional
@Component
public class Scraper {

    @Autowired
    NewsRepository newsRepository;

    public void scraping(){
        Scraper s = new Scraper();
        ArrayList<String> endpoints = new ArrayList<>();
        ArrayList<String> nameCategory = new ArrayList<>();

        ArrayList<News> newsToAdd = new ArrayList<>();

        endpoints.add("https://www.flaminiovilla.it/category/news/");
        endpoints.add("https://www.flaminiovilla.it/category/news/editoriali/");
        endpoints.add("https://www.flaminiovilla.it/category/eventi/");

        nameCategory.add("News");
        nameCategory.add("Comunicazioni");
        nameCategory.add("Eventi");

        for (int i = 0; i < 3; i++) {

            ArrayList<String> articoli = s.getArticleLinks(endpoints.get(i));
            for (String art : articoli) {
                News newNews = s.parseContent(art , nameCategory.get(i));
                newsToAdd.add(newNews);
                newsRepository.save(newNews);
                System.out.println(newNews);
            }
        }
        System.out.println(newsToAdd);
    }


    @SneakyThrows
    public ArrayList<String> getArticleLinks(String endpoint) {

        ArrayList<String> articleLinks = new ArrayList<>();
        Document doc = Jsoup.connect(endpoint).get();
        Elements articleContainer = doc.getElementsByClass("article-container");
        articleContainer.forEach(article -> {
            article.getElementsByClass("post").forEach(post -> {

                articleLinks.add(post.select("a").attr("href"));
//                System.out.println(post.select("a").attr("href"));
            });
        });
        return articleLinks;
    }

    @SneakyThrows
    public News parseContent(String link , String nameCategory) {
        Document doc = Jsoup.connect(link).get();
        Elements entryContent = doc.getElementsByClass("entry-content");
        String dateString = "";
        if (!doc.getElementsByClass("entry-date published").attr("datetime").equals(""))
            dateString = doc.getElementsByClass("entry-date published").attr("datetime");
        else
            dateString = doc.getElementsByClass("entry-date published updated").attr("datetime");

        return News.builder()
                .title(doc.title())
                .description(doc.getElementsByClass("entry-content").select("p").text())
                .date(dateString.substring(0, 10))
                .section(nameCategory)
                .image(entryContent.select("img").attr("data-orig-file"))
                .build();
    }


}