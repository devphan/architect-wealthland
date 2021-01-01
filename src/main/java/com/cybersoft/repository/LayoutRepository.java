package com.cybersoft.repository;


import com.cybersoft.model.admin.Layout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LayoutRepository extends JpaRepository<Layout, Long> {
    @Override
    @Modifying
    @Query("update Layout p set p.deleted=1 where p.id=:id")
    void deleteById(@Param("id") Long id);

    @Query(value = "select e from Layout e order by e.id DESC ")
    Page<Layout> findAllPage(Pageable pageable);

    @Query( value = "select * from layouts where layouts.type= 'about'" +"and  layouts.tab=:tab" , nativeQuery = true)
    Layout findByAboutTab(String tab);

    @Query( value = "select * from layouts where layouts.type= 'partner'" +"and  layouts.tab=:tab" , nativeQuery = true)
    Layout findByPartnerTab(String tab);

    @Query( value = "select * from layouts where layouts.deleted = 0 and layouts.type= 'about'" , nativeQuery = true)
    List<Layout> introduce();

    @Query( value = "select * from layouts where layouts.type= 'partner'" , nativeQuery = true)
    List<Layout> introducePartner();


}
