package kiosk.api.order.service.orderGetDate.monthly;

import jakarta.validation.Valid;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;

public interface MonthlyOrderQueryService {

    // 월간 조회
    OrderDateTotalResponse getMonthlyOrder(OrderDateRequest request);

}
