package kiosk.api.order.service.orderGetDate;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateTimeRangeRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;

public interface OrderQueryService {
    OrderDateTotalResponse getDailyOrder(OrderDateRequest request);

    OrderDateTotalResponse getDailyTimeOrder(OrderDateTimeRangeRequest request);
}
