package com.cybersoft.repository;

import com.cybersoft.model.admin.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Override
    @Modifying
    @Query("update Project p set p.deleted=1 where p.id=:id")
    void deleteById(@Param("id") Long id);
    @Query(value = "SELECT * FROM project\n" + "WHERE project.status = 'Active'\n" + "ORDER BY project.priority ASC , project.time DESC LIMIT 6", nativeQuery = true)
    List<Project> findHotest();

    @Query(value = "select e from Project e order by e.id DESC ")
    Page<Project> findAllPage(Pageable pageable);
}
