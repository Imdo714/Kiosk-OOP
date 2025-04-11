package kiosk.api.order.controller;

import kiosk.api.ApiResponse;
import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.request.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDailyResponse;
import kiosk.api.order.domain.dto.response.OrderResponse;
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

    @PostMapping("/order/new")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreateRequest request){
        return ApiResponse.ok(orderService.createOrder(request));
    }

    @GetMapping("/order/date")
    public ApiResponse<OrderDailyResponse> getDailyOrder(@RequestBody OrderDateRequest request){
        return ApiResponse.ok(orderService.getDailyOrder(request));
    }

}
