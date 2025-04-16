package kiosk.api.order.service.orderGetDate.time;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateTimeRangeRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.service.orderGetDate.OrderDateTimeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TimeOrderQueryServiceImpl implements TimeOrderQueryService{

    private final OrderDateTimeGenerator orderDateTimeGenerator;

    @Override
    public OrderDateTotalResponse getDailyTimeOrder(OrderDateTimeRangeRequest request) {
        return orderDateTimeGenerator.getOrderDailyResponse(request.getStartDateTime(), request.getEndDateTime(), request.getDate());
    }

}
