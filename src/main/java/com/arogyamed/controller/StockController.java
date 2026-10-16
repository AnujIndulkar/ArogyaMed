package com.arogyamed.controller;

import com.arogyamed.dto.RestockRequestDTO;
import com.arogyamed.dto.StockItemDTO;
import com.arogyamed.service.StockService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // Every medicine with its stock and status
    @GetMapping
    public List<StockItemDTO> list() {
        return stockService.listStock();
    }

    // Add units to a medicine's stock
    @PatchMapping("/{medicineId}/restock")
    public StockItemDTO restock(@PathVariable Long medicineId,
                                @Valid @RequestBody RestockRequestDTO body) {
        return stockService.restock(medicineId, body.getQuantity());
    }
}