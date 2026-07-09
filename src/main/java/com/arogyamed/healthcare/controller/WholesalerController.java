package com.arogyamed.healthcare.controller;

import com.arogyamed.healthcare.dto.WholesalerRequestDTO;
import com.arogyamed.healthcare.dto.WholesalerResponseDTO;
import com.arogyamed.healthcare.service.WholesalerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wholesalers")
public class WholesalerController {

    @Autowired
    private WholesalerService wholesalerService;

    @PostMapping
    public WholesalerResponseDTO createWholesaler(@RequestBody WholesalerRequestDTO request) {
        return wholesalerService.createWholesaler(request);
    }

    @GetMapping("/{userId}")
    public WholesalerResponseDTO getWholesalerByUserId(@PathVariable Long userId) {
        return wholesalerService.getWholesalerByUserId(userId);
    }

    @PutMapping("/{userId}")
    public WholesalerResponseDTO updateWholesaler(@PathVariable Long userId, @RequestBody WholesalerRequestDTO request) {
        return wholesalerService.updateWholesaler(userId, request);
    }
}
