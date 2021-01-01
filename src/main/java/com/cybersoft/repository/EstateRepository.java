package com.cybersoft.repository;

import com.cybersoft.model.admin.Estate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstateRepository extends JpaRepository<Estate, Long> {
    @Override
    @Modifying
    @Query("update Estate p set p.deleted=1 where p.id=:id")
    void deleteById(@Param("id") Long id);

    @Query("select e from Estate e where e.project.id=:id")
    List<Estate> findByProject(Long id);

    @Query("select e from Estate e where e.price=:price")
    List<Estate> findByPrice(Long price);

    @Query("select e from Estate e where e.area=:area")
    List<Estate> findByArea(Long area);

    @Query("select e from Estate e where e.direction =:direction")
    List<Estate> findByDirection(String direction);

    @Query("select e from Estate e where e.city.id =:city")
    List<Estate> findByCity(Long city);

    @Query(value = "SELECT * FROM estates\n"  + "WHERE  estates.is_hot = true " + "ORDER BY estates.checkin DESC LIMIT 3", nativeQuery = true)
    List<Estate> findHotest();

    @Query(value = "select e from Estate e order by e.id DESC ")
    Page<Estate> findAllPage(Pageable pageable);

    @Query("select e from Estate e where e.category.id =:category")
    List<Estate> findByCategory(Long category);

    @Query(value = "SELECT * FROM estates\n" + "WHERE estates.price <=:max AND estates.price >=:min ORDER BY estates.checkin", nativeQuery = true)
    List<Estate> filterPrice(Long min,Long max);
}
