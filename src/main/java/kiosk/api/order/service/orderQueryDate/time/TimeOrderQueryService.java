package kiosk.api.order.service.orderQueryDate.time;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateTimeRangeRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;

public interface TimeOrderQueryService {

    // 시간 대 별 조회
    OrderDateTotalResponse getDailyTimeOrder(OrderDateTimeRangeRequest request);
}
