package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.date, obj.amount, obj.seller.name) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%'))")
    Page<SaleMinDTO> findSalesReport(@Param("minDate") LocalDate minDate,
                                     @Param("maxDate") LocalDate maxDate,
                                     @Param("sellerName") String sellerName,
                                     Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SellerMinDTO(s.seller.name, SUM(s.amount)) " +
            "FROM Sale s " +
            "WHERE s.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY s.seller.name")
    List<SellerMinDTO> summaryBySeller(@Param("minDate") LocalDate minDate,
                                       @Param("maxDate") LocalDate maxDate);
}
