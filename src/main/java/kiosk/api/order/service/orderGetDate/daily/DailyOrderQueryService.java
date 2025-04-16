package kiosk.api.order.service.orderGetDate.daily;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;

public interface DailyOrderQueryService {

    // 일일 조회
    OrderDateTotalResponse getDailyOrder(OrderDateRequest request);

}
