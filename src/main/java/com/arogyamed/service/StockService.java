package com.arogyamed.service;

import com.arogyamed.dto.StockItemDTO;

import java.util.List;

public interface StockService {

    List<StockItemDTO> listStock();

    StockItemDTO restock(Long medicineId, int quantity);
}