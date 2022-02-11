package com.example.ArticleDownloader.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private final String COLON = ":";

    private String title;
    private String description;
    private String author;

    @Override
    public String toString() {
        return title + COLON + description + COLON + author;
    }

}
