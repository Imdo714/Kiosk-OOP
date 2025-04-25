package kiosk.api.order.service.orderQueryDate;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.domain.dto.response.OrderListResponse;
import kiosk.api.order.domain.dto.request.OrderDTO;
import kiosk.api.order.repository.OrderRepository;
import kiosk.api.stats.bestMenu.domain.dto.request.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderSalesHistoryServiceImpl implements OrderSalesHistoryService {

    private final OrderRepository orderRepository;
    protected final OrderDateTimeGenerator orderDateTimeGenerator;

    @Override
    public OrderDateTotalResponse getSalesReportByPeriod(OrderDateRequest request) {
        DateRange dateRange = request.getDateType().getDateRange(request.getDate());

        return orderDateTimeGenerator.getOrderDailyResponse(dateRange.start, dateRange.end, request.getDate());
    }

    @Override
    public OrderListResponse getOrderHistoryByPeriod(OrderDateRequest request, Pageable pageable) {
        DateRange dateRange = request.getDateType().getDateRange(request.getDate());
        Page<OrderDTO> order2 = orderRepository.findRangeDateOrder(dateRange.start, dateRange.end, pageable);

        return OrderListResponse.of(order2);
    }

}
