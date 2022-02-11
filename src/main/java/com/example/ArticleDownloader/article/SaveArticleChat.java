package com.example.ArticleDownloader.article;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Component
public class SaveArticleChat {

    @Autowired
    ArticleService articleService;

    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        List<Article> articleList;
        String filePath = getFilePath(scanner);
        while (!isTxtFile(filePath)) {
            System.out.println("");
        }
        String apiKey = getApiKey(scanner);
        try{
            articleList = articleService.getArticlesFromAPI(apiKey);
            articleService.saveArticleListToTxt(filePath, articleList);
            System.out.println("Udało się! Plik został zapisany.");
        } catch (InterruptedException | IOException e) {
            log.error(e.getMessage());
            System.out.println("Coś poszło nie tak, spróbuj ponownie.");
            startChat();
        }
    }

    private String getFilePath(Scanner scanner) {
        System.out.println("Podaj ścieżkę pliku txt:");
        return scanner.nextLine();
    }

    private String getApiKey(Scanner scanner) {
        System.out.println("Podaj klucz API:");
        return scanner.nextLine();
    }

    private boolean isTxtFile(String filePath) {
        try {
            return filePath.substring(filePath.lastIndexOf(".")).equals(".txt");
        } catch (Exception e) {
            return false;
        }
    }

}
