package com.example.seo.demo;

import com.example.seo.demo.entity.ApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ApiRepository extends JpaRepository<ApiEntity, Long> {
    List<ApiEntity> findByCityCountyNameContainingAndFacilityNameContainingAndOwningInstitutionNameContaining(String cityCountyName,String facilityName, String owningInstitutionName); // 이름으로 검색

    // 시, 군, 명으로 검색
    @Query("SELECT a FROM ApiEntity a WHERE " +
            "(:cityCountyName IS NULL OR a.cityCountyName LIKE %:cityCountyName%) AND " +
            "(:facilityName IS NULL OR a.facilityName LIKE %:facilityName%) AND " +
            "(:owningInstitutionName IS NULL OR a.owningInstitutionName LIKE %:owningInstitutionName%)")
    List<ApiEntity> searchByFilters(
            @Param("cityCountyName") String cityCountyName,
            @Param("facilityName") String facilityName,
            @Param("owningInstitutionName") String owningInstitutionName);

    boolean existsByFacilityName(String facilityName);

    Optional<ApiEntity> findByFacilityName(String facilityName);
}