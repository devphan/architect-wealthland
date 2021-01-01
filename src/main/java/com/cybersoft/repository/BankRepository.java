package com.cybersoft.repository;

import com.cybersoft.model.admin.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BankRepository extends JpaRepository<Bank, Long> {
    @Override
    @Modifying
    @Query("update Bank p set p.deleted=1 where p.id=:id")
    void deleteById(@Param("id") Long id);

    @Query(value = "select e from Bank e order by e.id DESC ")
    Page<Bank> findAllPage(Pageable pageable);
}
