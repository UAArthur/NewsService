package net.hauntedstudio.News.controller;

import net.hauntedstudio.News.dto.NewsPostDTO;
import net.hauntedstudio.News.model.NewsModel;
import net.hauntedstudio.News.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class NewsHtmlController {

    private final NewsService newsService;

    public NewsHtmlController(NewsService newsService) {
        this.newsService = newsService;
    }


    // localhost:8085/news -> index.html
    @RequestMapping({""})
    public String index() {
        return "index";
    }

    // localhost:8085/news/{id} -> post.html
    @RequestMapping("/{id}")
    public String post(@PathVariable String id, Model model) {
        NewsModel newsModel = newsService.findById(Long.parseLong(id));
        NewsPostDTO newsPostDTO = new NewsPostDTO(newsModel.getTitle(), newsModel.getContent(),
                newsModel.getCategory().name(), newsModel.getCreatedAt(), newsModel.getPlatform());
        model.addAttribute("newsPost", newsPostDTO);
        return "post";
    }

}
