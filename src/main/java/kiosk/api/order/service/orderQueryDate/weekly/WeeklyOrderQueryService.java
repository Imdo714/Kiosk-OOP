package kiosk.api.order.service.orderQueryDate.weekly;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;

public interface WeeklyOrderQueryService {

    // 주간 조회
    OrderDateTotalResponse getWeeklyOrder(OrderDateRequest request);

}
