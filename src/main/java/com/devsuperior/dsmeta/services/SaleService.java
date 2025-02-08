package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> findSalesReport(String minDate, String maxDate, String sellerName, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate max = maxDate == null || maxDate.isEmpty() ? today : LocalDate.parse(maxDate);
		LocalDate min = minDate == null || minDate.isEmpty() ? max.minusYears(1L) : LocalDate.parse(minDate);
		String name = sellerName == null ? "" : sellerName;

		return repository.findSalesReport(min, max, name, pageable);
	}

	public List<SellerMinDTO> summaryBySeller(String minDate, String maxDate) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate max = maxDate == null || maxDate.isEmpty() ? today : LocalDate.parse(maxDate);
		LocalDate min = minDate == null || minDate.isEmpty() ? max.minusYears(1L) : LocalDate.parse(minDate);

		return repository.summaryBySeller(min, max);
	}


}
