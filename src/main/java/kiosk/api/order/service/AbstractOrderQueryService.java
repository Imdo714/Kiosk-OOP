package kiosk.api.order.service;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.service.orderQueryDate.OrderDateTimeGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class AbstractOrderQueryService {

    protected final OrderDateTimeGenerator orderDateTimeGenerator;

    protected AbstractOrderQueryService(OrderDateTimeGenerator orderDateTimeGenerator) {
        this.orderDateTimeGenerator = orderDateTimeGenerator;
    }

    protected abstract LocalDateTime getStartDateTime(LocalDate date);
    protected abstract LocalDateTime getEndDateTime(LocalDate date);

    // 공통 처리 로직
    protected OrderDateTotalResponse getOrderResponse(OrderDateRequest request) {
        LocalDateTime start = getStartDateTime(request.getDate());
        LocalDateTime end = getEndDateTime(request.getDate());

        return orderDateTimeGenerator.getOrderDailyResponse(start, end, request.getDate());
    }
}
