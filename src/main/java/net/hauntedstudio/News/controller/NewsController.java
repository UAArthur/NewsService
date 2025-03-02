package net.hauntedstudio.News.controller;

import lombok.Getter;
import net.hauntedstudio.News.dto.CreateNewsRequest;
import net.hauntedstudio.News.dto.NewsSummaryDTO;
import net.hauntedstudio.News.model.NewsModel;
import net.hauntedstudio.News.service.NewsService;
import net.hauntedstudio.News.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/create")
    public ResponseEntity<?> createNews(@RequestBody CreateNewsRequest request) {
        if (request.getTitle() == null || request.getCategory() == 0 || request.getPlatform() == null || request.getContent() == null) {
            return ResponseEntity.badRequest().build();
        }
        // Create news article
        NewsModel news = new NewsModel();
        news.setTitle(request.getTitle());
        news.setCategory(NewsModel.Category.fromValue(request.getCategory()));
        news.setPlatform(request.getPlatform());
        news.setContent(request.getContent());

        // Save news article
        newsService.createNews(news);

        Logger.debug("News article created: " + news.getTitle() + " - " + news.getCategory() + " - " + news.getPlatform());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/latest")
    public ResponseEntity<List<NewsSummaryDTO>> getLatestNews(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        List<NewsModel> newsList = newsService.getLatestNews(page, pageSize);

        if (newsList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<NewsModel> modifiableNewsList = new ArrayList<>(newsList);
        modifiableNewsList.sort((n1, n2) -> Long.compare(n2.getId(), n1.getId()));

        List<NewsSummaryDTO> newsSummaryList = modifiableNewsList.stream()
                .map(NewsSummaryDTO::new)
                .toList();

        return ResponseEntity.ok(newsSummaryList);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<NewsSummaryDTO>> getNewsByCategory(@PathVariable("category") int category, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        List<NewsModel> newsList = newsService.getNewsByCategory(page, pageSize, NewsModel.Category.fromValue(category));

        if (newsList.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<NewsSummaryDTO> newsSummaryList = newsList.stream()
                .map(NewsSummaryDTO::new)
                .toList();

        return ResponseEntity.ok(newsSummaryList);
    }

    @GetMapping("/category/{category}/count")
    public ResponseEntity<Integer> countByCategory(@PathVariable("category") int category) {
        return ResponseEntity.ok(newsService.countByCategory(NewsModel.Category.fromValue(category)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsSummaryDTO>> getNewsByTitle(@RequestParam("query") String title) {
        List<NewsModel> newsList = newsService.findByTitleContaining(title);

        if (newsList.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<NewsSummaryDTO> newsSummaryList = newsList.stream()
                .map(NewsSummaryDTO::new)
                .toList();

        return ResponseEntity.ok(newsSummaryList);
    }



}