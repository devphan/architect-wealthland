package com.cybersoft.repository;

import com.cybersoft.model.admin.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    @Override
    @Modifying
    @Query("update News p set p.deleted=1 where p.id=:id")
    void deleteById(@Param("id") Long id);

    @Query(value = "select e from News e order by e.id DESC ")
    Page<News> findAllPage(Pageable pageable);

    @Query("select e from News e where e.category_news.id =:category_news order by e.checkin desc")
    List<News> findByCategoryNews(Long category_news);

    @Query(value = "SELECT * FROM news\n" + "WHERE news.estate.id =:estate\n" + " ORDER BY news.checkin DESC",nativeQuery = true)
    List<News> findByEstate(Long estate);

    @Override
    @Query(value = "select * from news where news.deleted = 0 order by news.id desc\n ", nativeQuery = true)
    List<News> findAll();


//    List<News> findByCategory_news(Category_News id);
}
