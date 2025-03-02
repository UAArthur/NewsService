package net.hauntedstudio.News.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "news")
public class NewsModel {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, updatable = false)
    private final LocalDate createdAt = LocalDate.now();

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    @Setter
    @Column(nullable = false, length = 100)
    private String platform;

    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 18)
    private long userId; //Creator

    // Constructors
    public NewsModel() {}

    @Getter
    public enum Category {
        All(0),
        NEWS(1),
        MAINTENANCES(2),
        EVENTS(3),
        UPDATES(4),
        SHOP(5);

        private final int value;

        Category(int value) {
            this.value = value;
        }

        public static Category fromValue(int value) {
            for (Category category : Category.values()) {
                if (category.value == value) {
                    return category;
                }
            }
            throw new IllegalArgumentException("Invalid category value: " + value);
        }
    }
}