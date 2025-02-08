package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/report")
	public ResponseEntity<Page<SaleMinDTO>> findSalesReport(
			@RequestParam(value = "minDate", required = false) String minDate,
			@RequestParam(value = "maxDate", required = false) String maxDate,
			@RequestParam(value = "name", required = false) String sellerName,
			Pageable pageable) {
		Page<SaleMinDTO> dto = service.findSalesReport(minDate, maxDate, sellerName, pageable);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/summary")
	public ResponseEntity<List<SellerMinDTO>> summaryBySeller(
			@RequestParam(value = "minDate", required = false) String minDate,
			@RequestParam(value = "maxDate", required = false) String maxDate) {
		List<SellerMinDTO> dto = service.summaryBySeller(minDate, maxDate);
		return ResponseEntity.ok(dto);
	}
}
