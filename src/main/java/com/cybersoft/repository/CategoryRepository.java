package com.cybersoft.repository;

import com.cybersoft.model.admin.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    @Modifying
    @Query("update Category p set p.deleted=1 where p.id=:id")
    void deleteById(@Param("id") Long id);

    @Query(value = "select e from Category e order by e.id DESC ")
    Page<Category> findAllPage(Pageable pageable);

    @Override
    @Query(value = "select * from categorys order by categorys.id asc\n ", nativeQuery = true)
    List<Category> findAll();
}
