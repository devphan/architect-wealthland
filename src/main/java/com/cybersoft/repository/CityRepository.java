package com.cybersoft.repository;

import com.cybersoft.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Long> {
    @Query("select e from City e where e.province.id=:id")
    List<City> findByAddress(@Param("id") Long id);

}
