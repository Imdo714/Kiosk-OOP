package kiosk.api.order.service;

import kiosk.api.order.domain.request.OrderCreateRequest;
import kiosk.api.order.domain.request.OrderDateRequest;
import kiosk.api.order.domain.response.OrderDailyResponse;
import kiosk.api.order.domain.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderCreateRequest request);

    OrderDailyResponse getDailyOrder(OrderDateRequest request);
}
