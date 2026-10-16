package com.arogyamed.dto;

import com.arogyamed.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Body of the staff status change: just the new status. */
@Getter
@Setter
@NoArgsConstructor
public class StatusUpdateRequestDTO {

    @NotNull(message = "Status is required")
    private OrderStatus status;
}
