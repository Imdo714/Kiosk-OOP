package kiosk.api.order.service.orderQueryDate;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.domain.dto.response.OrderListResponse;
import org.springframework.data.domain.Pageable;

public interface OrderSalesHistoryService {

    OrderDateTotalResponse getSalesReportByPeriod(OrderDateRequest request);

    OrderListResponse getOrderHistoryByPeriod(OrderDateRequest request, Pageable pageable);
}
