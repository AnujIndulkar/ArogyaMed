package com.arogyamed.healthcare.service;

import com.arogyamed.healthcare.dto.WholesalerRequestDTO;
import com.arogyamed.healthcare.dto.WholesalerResponseDTO;

public interface WholesalerService {

    WholesalerResponseDTO createWholesaler(WholesalerRequestDTO request);

    WholesalerResponseDTO getWholesalerByUserId(Long userId);

    WholesalerResponseDTO updateWholesaler(Long userId, WholesalerRequestDTO request);
}
