package kiosk.api.order.controller;

import jakarta.validation.Valid;
import kiosk.api.ApiResponse;
import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateTimeRangeRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.domain.dto.response.OrderResponse;
import kiosk.api.order.service.orderGetDate.OrderQueryService;
import kiosk.api.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderQueryService orderQueryService;

    @PostMapping("/order/new")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreateRequest request){
        return ApiResponse.ok(orderService.createOrder(request));
    }

    @GetMapping("/order/date")
    public ApiResponse<OrderDateTotalResponse> getDailyOrder(@Valid @RequestBody OrderDateRequest request){
        return ApiResponse.ok(orderQueryService.getDailyOrder(request));
    }

    @GetMapping("/order/dateTime")
    public ApiResponse<OrderDateTotalResponse> getDailyTimeOrder(@Valid @RequestBody OrderDateTimeRangeRequest request){
        return ApiResponse.ok(orderQueryService.getDailyTimeOrder(request));
    }

}
