package kiosk.api.discount.service;

import kiosk.api.discount.domain.dto.request.DiscountRequest;
import kiosk.api.discount.domain.dto.response.DiscountResponse;

public interface DiscountService {

    DiscountResponse createDiscount(DiscountRequest request);

}
