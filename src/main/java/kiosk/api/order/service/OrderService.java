package kiosk.api.order.service;

import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateTimeRangeRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.domain.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderCreateRequest request);

    OrderDateTotalResponse getDailyOrder(OrderDateRequest request);

    OrderDateTotalResponse getDailyTimeOrder(OrderDateTimeRangeRequest request);
}
