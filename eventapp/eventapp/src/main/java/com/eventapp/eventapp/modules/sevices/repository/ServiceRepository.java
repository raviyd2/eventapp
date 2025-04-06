package com.eventapp.eventapp.modules.sevices.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventapp.eventapp.modules.sevices.model.ServiceEntity;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    
    @Query("SELECT s FROM ServiceEntity s WHERE " +
           "(:category IS NULL OR s.category = :category) AND " +
           "(:minPrice IS NULL OR s.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR s.price <= :maxPrice)")
    Page<ServiceEntity> findByFilters(
        @Param("category") String category,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        Pageable pageable);
    
    Page<ServiceEntity> findByVendorId(Long vendorId, Pageable pageable);
    
    // Add this method if you need to find available services
    Page<ServiceEntity> findByVendorIdAndAvailableTrue(Long vendorId, Pageable pageable);
}