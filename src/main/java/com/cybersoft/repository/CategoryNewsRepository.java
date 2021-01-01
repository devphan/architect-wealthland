package com.cybersoft.repository;

import com.cybersoft.model.admin.Category_News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryNewsRepository extends JpaRepository<Category_News, Long> {
    @Override
    @Modifying
    @Query("update Category_News p set p.deleted=1 where p.id=:id")
    void deleteById(@Param("id") Long id);

    @Query(value = "select e from Category_News e order by e.id DESC ")
    Page<Category_News> findAllPage(Pageable pageable);

    @Override
    @Query(value = "select * from category_news where category_news.deleted = 0 order by category_news.id asc\n ", nativeQuery = true)
    List<Category_News> findAll();
}
