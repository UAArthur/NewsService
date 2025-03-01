package net.hauntedstudio.News.service;

import net.hauntedstudio.News.model.NewsModel;
import net.hauntedstudio.News.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    // Create news article
    public void createNews(NewsModel news) {
        newsRepository.save(news);
    }

    public List<NewsModel> getLatestNews(int pageNumber, int pageSize) {
        Page<NewsModel> newsPage = newsRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(pageNumber, pageSize));
        return newsPage.getContent();
    }

    public List<NewsModel> getNewsByCategory(int pageNumber, int pageSize, NewsModel.Category category) {
        Page<NewsModel> newsPage = newsRepository.findAllByCategoryOrderByCreatedAtDesc(category, PageRequest.of(pageNumber, pageSize));
        return newsPage.getContent();
    }

    public int countByCategory(NewsModel.Category category) {
        return (category != NewsModel.Category.All) ? newsRepository.countByCategory(category) : newsRepository.countAll();
    }

    public List<NewsModel> findByTitleContaining(String title) {
        return newsRepository.findByTitleContaining(title);
    }

    public NewsModel findById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }
}
