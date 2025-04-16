package kiosk.api.order.service.orderGetDate.daily;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.service.orderGetDate.OrderDateTimeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DailyOrderQueryServiceImpl implements DailyOrderQueryService{

    private final OrderDateTimeGenerator orderDateTimeGenerator;

    @Override
    public OrderDateTotalResponse getDailyOrder(OrderDateRequest request) {
        LocalDateTime start = request.getDate().atStartOfDay();
        LocalDateTime end = request.getDate().plusDays(1).atStartOfDay();

        return orderDateTimeGenerator.getOrderDailyResponse(start, end, request.getDate());
    }

}
