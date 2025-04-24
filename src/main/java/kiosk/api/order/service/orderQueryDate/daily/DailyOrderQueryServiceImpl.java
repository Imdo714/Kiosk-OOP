package kiosk.api.order.service.orderQueryDate.daily;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.service.AbstractOrderQueryService;
import kiosk.api.order.service.orderQueryDate.OrderDateTimeGenerator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DailyOrderQueryServiceImpl extends AbstractOrderQueryService implements DailyOrderQueryService{

    protected DailyOrderQueryServiceImpl(OrderDateTimeGenerator orderDateTimeGenerator) {
        super(orderDateTimeGenerator);
    }

    @Override
    protected LocalDateTime getStartDateTime(LocalDate date) {
        return date.atStartOfDay();
    }

    @Override
    protected LocalDateTime getEndDateTime(LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }

    @Override
    public OrderDateTotalResponse getDailyOrder(OrderDateRequest request) {
        return getOrderResponse(request);
    }
}
