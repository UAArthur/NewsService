//package net.hauntedstudio.News.dto;
//
//import lombok.Getter;
//import lombok.Setter;
//import net.hauntedstudio.News.model.NewsModel;
//
//import java.time.LocalDate;
//
//@Getter
//@Setter
//public class NewsSummaryDTO {
//    private Long id;
//    private String title;
//    private LocalDate createdAt;
//    private int category;
//    private String platform;
//
//    public NewsSummaryDTO() {
//    }
//
//    public NewsSummaryDTO(NewsModel news) {
//        this.id = news.getId();
//        this.title = news.getTitle();
//        this.createdAt = news.getCreatedAt();
//        this.category = news.getCategory().getValue();
//        this.platform = news.getPlatform();
//    }
//
//}
