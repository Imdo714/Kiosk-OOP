package kiosk.api.order.service.orderQueryDate.monthly;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;

public interface MonthlyOrderQueryService {

    // 월간 조회
    OrderDateTotalResponse getMonthlyOrder(OrderDateRequest request);

}
