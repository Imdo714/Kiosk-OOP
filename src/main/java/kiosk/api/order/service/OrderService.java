package kiosk.api.order.service;

import kiosk.api.order.domain.request.OrderCreateRequest;
import kiosk.api.order.domain.response.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderCreateRequest request);

}
