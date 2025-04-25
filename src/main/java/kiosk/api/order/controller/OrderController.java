package kiosk.api.order.controller;

import jakarta.validation.Valid;
import kiosk.api.ApiResponse;
import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.domain.dto.response.OrderListResponse;
import kiosk.api.order.domain.dto.response.OrderResponse;
import kiosk.api.order.service.OrderService;
import kiosk.api.order.service.orderQueryDate.OrderSalesHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderSalesHistoryService orderHistoryService;

    @PostMapping("/order/new")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreateRequest request){
        return ApiResponse.ok(orderService.createOrder(request));
    }

    @GetMapping("/order/sales")
    public ApiResponse<OrderDateTotalResponse> getSalesReport(@Valid @RequestBody OrderDateRequest request){
        return ApiResponse.ok(orderHistoryService.getSalesReportByPeriod(request));
    }

    @GetMapping("/order/history")
    public ApiResponse<OrderListResponse> getOrderHistoryList(@Valid @RequestBody OrderDateRequest request, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.ok(orderHistoryService.getOrderHistoryByPeriod(request, pageable));
    }

}
