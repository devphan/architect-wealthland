package com.cybersoft.repository;

import com.cybersoft.model.admin.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Override
    @Modifying
    @Query("update Client p set p.deleted=1 where p.id=:id")
    void deleteById(@Param("id") Long id);


    @Query(value = "select * from clients order by id DESC", nativeQuery = true)
    Page<Client> findAllPage(Pageable pageable);
}
