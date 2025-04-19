package kiosk.api.discount.controller;

import jakarta.validation.Valid;
import kiosk.api.ApiResponse;
import kiosk.api.discount.domain.dto.request.DiscountRequest;
import kiosk.api.discount.domain.dto.response.DiscountResponse;
import kiosk.api.discount.service.DiscountService;
import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DiscountController {

    private final DiscountService discountService;
    private final OrderServiceImpl orderService;

    @PostMapping("/discount")
    public ApiResponse<DiscountResponse> createDiscount(@Valid @RequestBody DiscountRequest request){
        return ApiResponse.ok(discountService.createDiscount(request));
    }

}
