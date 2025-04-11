package kiosk.api.order.service;

import kiosk.api.order.domain.dto.request.OrderCreateRequest;
import kiosk.api.order.domain.dto.request.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDailyResponse;
import kiosk.api.order.domain.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderCreateRequest request);

    OrderDailyResponse getDailyOrder(OrderDateRequest request);
}
