package com.example.ArticleDownloader;

import com.example.ArticleDownloader.article.SaveArticleChat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ArticleDownloaderApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(ArticleDownloaderApplication.class, args);

		SaveArticleChat chat = appContext.getBean(SaveArticleChat.class);
		chat.startChat();
	}

}
