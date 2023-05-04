package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {

    Optional<RealEstate> getRealEstateById(Long id);

    @Query("select re from RealEstate re where re.sqMeters >= ?1 and re.sqMeters <= ?2 order by re.name asc ")
    List<RealEstate> filterAllRealEstatesAsc(Integer bottomSqArea, Integer topSqArea);

    @Query("select re from RealEstate re where re.sqMeters >= ?1 and re.sqMeters <= ?2 order by re.name desc ")
    List<RealEstate> filterAllRealEstatesDesc(Integer bottomSqArea, Integer topSqArea);

    @Query("select re from RealEstate re where re.sqMeters >= ?1 and re.sqMeters <= ?2 and re.owner.id = ?3 order by re.name asc ")
    List<RealEstate> filterRealEstatesByOwnerAsc(Integer bottomSqArea, Integer topSqArea, Long id);

    @Query("select re from RealEstate re where re.sqMeters >= ?1 and re.sqMeters <= ?2 and re.owner.id = ?3 order by re.name desc ")
    List<RealEstate> filterRealEstatesByOwnerDesc(Integer bottomSqArea, Integer topSqArea, Long id);

}
