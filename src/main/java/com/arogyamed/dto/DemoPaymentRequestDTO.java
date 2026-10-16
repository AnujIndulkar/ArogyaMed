package com.arogyamed.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Body of the demo payment call. The "Fail" button in the demo window sends simulateFailure = true. */
@Getter
@Setter
@NoArgsConstructor
public class DemoPaymentRequestDTO {

    private boolean simulateFailure;
}
