package com.cybersoft.repository;

import com.cybersoft.model.admin.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    @Override
    @Modifying
    @Query("update Banner p set p.deleted=1 where p.id=:id")
    void deleteById(@Param("id") Long id);

    @Query(value = "select e from Banner e order by e.id DESC ")
    Page<Banner> findAllPage(Pageable pageable);

    @Query(value = "select e from Banner e order by e.id DESC ")
    Banner findBanner();
}
