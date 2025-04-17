package kiosk.api.order.service.orderQueryDate.monthly;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.service.AbstractOrderQueryService;
import kiosk.api.order.service.orderQueryDate.OrderDateTimeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
public class MonthlyOrderQueryServiceImpl extends AbstractOrderQueryService implements MonthlyOrderQueryService{

    protected MonthlyOrderQueryServiceImpl(OrderDateTimeGenerator orderDateTimeGenerator) {
        super(orderDateTimeGenerator);
    }

    @Override
    protected LocalDateTime getStartDateTime(LocalDate date) {
        return date.withDayOfMonth(1).atStartOfDay();
    }

    @Override
    protected LocalDateTime getEndDateTime(LocalDate date) {
        return date.plusMonths(1).withDayOfMonth(1).atStartOfDay();
    }

    @Override
    public OrderDateTotalResponse getMonthlyOrder(OrderDateRequest request) {
        return getOrderResponse(request);
    }
}