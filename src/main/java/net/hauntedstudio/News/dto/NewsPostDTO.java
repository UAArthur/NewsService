package net.hauntedstudio.News.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NewsPostDTO {
    private String title;
    private String content;
    private String category;
    private LocalDate date;
    private String platform;

    public NewsPostDTO() {}

    public NewsPostDTO(String title, String content, String category, LocalDate date, String platform) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.date = date;
        this.platform = platform;
    }
}
