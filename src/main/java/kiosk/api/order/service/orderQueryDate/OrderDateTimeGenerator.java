package kiosk.api.order.service.orderQueryDate;

import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.domain.dto.request.OrderDTO;
import kiosk.api.order.repository.OrderRepository;
import kiosk.api.order.service.detaileLogic.OrderSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderDateTimeGenerator  {

    private final OrderRepository orderRepository;

    public OrderDateTotalResponse getOrderDailyResponse(LocalDateTime start, LocalDateTime end, LocalDate request) {
        List<OrderDTO> order = orderRepository.findDailyOrder(start, end);
        OrderSummary orderSummary = OrderSummary.calculateTotal(order);

        return new OrderDateTotalResponse(orderSummary.getTotalPrice(), orderSummary.getTotalQuantity(), request);
    }

}
