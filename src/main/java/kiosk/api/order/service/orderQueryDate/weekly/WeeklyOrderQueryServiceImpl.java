package kiosk.api.order.service.orderQueryDate.weekly;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.service.AbstractOrderQueryService;
import kiosk.api.order.service.orderQueryDate.OrderDateTimeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
public class WeeklyOrderQueryServiceImpl extends AbstractOrderQueryService implements WeeklyOrderQueryService {

    protected WeeklyOrderQueryServiceImpl(OrderDateTimeGenerator orderDateTimeGenerator) {
        super(orderDateTimeGenerator);
    }

    @Override
    protected LocalDateTime getStartDateTime(LocalDate date) {
        return date.with(DayOfWeek.MONDAY).atStartOfDay();
    }

    @Override
    protected LocalDateTime getEndDateTime(LocalDate date) {
        return getStartDateTime(date).plusDays(7);
    }

    @Override
    public OrderDateTotalResponse getWeeklyOrder(OrderDateRequest request) {
        return getOrderResponse(request);
    }
}
