package net.hauntedstudio.News.repository;

import net.hauntedstudio.News.model.NewsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<NewsModel, Long> {
    Page<NewsModel> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<NewsModel> findAllByCategoryOrderByCreatedAtDesc(NewsModel.Category category, Pageable pageable);
    int countByCategory(NewsModel.Category category);
    @Query("SELECT COUNT(n) FROM NewsModel n")
    int countAll();
    //Find like by title
    List<NewsModel> findByTitleContaining(String title);
}


