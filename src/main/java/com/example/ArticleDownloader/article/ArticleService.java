package com.example.ArticleDownloader.article;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ArticleService {

    public static final String MAIN_URL = "https://newsapi.org/v2/top-headlines?country=pl&category=business&apiKey=";
    public static final String TITLE_KEY = "title";
    public static final String DESCRIPTION_KEY = "description";
    public static final String AUTHOR_KEY = "author";
    public static final String ARTICLES = "articles";
    public static final String EMPTY = "";


    public List<Article> getArticlesFromAPI(String apiKey) throws IOException, InterruptedException {
        JSONArray articlesJSON = getJSONArrayFromAPI(MAIN_URL, apiKey);
        List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < articlesJSON.length(); i++) {
            Article article = new Article();
            JSONObject articleJSON = (JSONObject) articlesJSON.get(i);
            article.setTitle(articleJSON.get(TITLE_KEY).equals(JSONObject.NULL) ? EMPTY : articleJSON.getString(TITLE_KEY));
            article.setDescription(articleJSON.get(DESCRIPTION_KEY).equals(JSONObject.NULL) ? EMPTY : articleJSON.getString(DESCRIPTION_KEY));
            article.setAuthor(articleJSON.get(AUTHOR_KEY).equals(JSONObject.NULL) ? EMPTY : articleJSON.getString(AUTHOR_KEY));
            articleList.add(article);
        }
        return articleList;
    }

    public JSONArray getJSONArrayFromAPI(String url, String apiKey) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + apiKey))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject responseJSON = new JSONObject(response.body());
        return responseJSON.getJSONArray(ARTICLES);
    }

    public void saveArticleListToTxt(String filePath, List<Article> articles) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        articles.forEach(a -> {
            try {
                writer.write(a.toString() + "\n");
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        });
        writer.close();
    }

}
