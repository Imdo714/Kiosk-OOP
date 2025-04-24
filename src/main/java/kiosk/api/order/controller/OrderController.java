package kiosk.api.order.controller;

import jakarta.validation.Valid;
import kiosk.api.ApiResponse;
import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateTimeRangeRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.domain.dto.response.OrderResponse;
import kiosk.api.order.service.OrderService;
import kiosk.api.order.service.OrderServiceImpl;
import kiosk.api.order.service.orderQueryDate.daily.DailyOrderQueryService;
import kiosk.api.order.service.orderQueryDate.monthly.MonthlyOrderQueryService;
import kiosk.api.order.service.orderQueryDate.time.TimeOrderQueryService;
import kiosk.api.order.service.orderQueryDate.weekly.WeeklyOrderQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;
    private final DailyOrderQueryService dailyOrderQueryService;
    private final TimeOrderQueryService timeOrderQueryService;
    private final WeeklyOrderQueryService weeklyOrderQueryService;
    private final MonthlyOrderQueryService monthlyOrderQueryService;

    @PostMapping("/order/new")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreateRequest request){
        return ApiResponse.ok(orderService.createOrder(request));
    }
    @GetMapping("/order/daily")
    public ApiResponse<OrderDateTotalResponse> getDailyOrder(@Valid @RequestBody OrderDateRequest request){
        return ApiResponse.ok(dailyOrderQueryService.getDailyOrder(request));
    }

    @GetMapping("/order/dateTime")
    public ApiResponse<OrderDateTotalResponse> getDailyTimeOrder(@Valid @RequestBody OrderDateTimeRangeRequest request){
        return ApiResponse.ok(timeOrderQueryService.getDailyTimeOrder(request));
    }

    @GetMapping("/order/weekly")
    public ApiResponse<OrderDateTotalResponse> getWeeklyOrder(@Valid @RequestBody OrderDateRequest request){
        return ApiResponse.ok(weeklyOrderQueryService.getWeeklyOrder(request));
    }

    @GetMapping("/order/monthly")
    public ApiResponse<OrderDateTotalResponse> getMonthlyOrder(@Valid @RequestBody OrderDateRequest request){
        return ApiResponse.ok(monthlyOrderQueryService.getMonthlyOrder(request));
    }

}
