package kiosk.api.order.service.orderGetDate;

import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface OrderGetDateTimeGenerator {
    OrderDateTotalResponse getOrderDailyResponse(LocalDateTime start, LocalDateTime end, LocalDate date);
}
