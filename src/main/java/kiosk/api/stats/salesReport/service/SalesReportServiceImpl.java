package kiosk.api.stats.salesReport.service;

import kiosk.api.order.domain.internal.OrderDTO;
import kiosk.api.order.repository.OrderRepository;
import kiosk.api.stats.bestMenu.domain.dto.request.BestMenuRequest;
import kiosk.api.stats.bestMenu.domain.dto.request.DateRange;
import kiosk.api.stats.salesReport.domain.response.OrderListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SalesReportServiceImpl implements SalesReportService {

    private final OrderRepository orderRepository;

    @Override
    public OrderListResponse getSalesReportByPeriod(BestMenuRequest request, Pageable pageable) {
        DateRange dateRange = request.getDateType().getDateRange(request.getDate());

        // 일일 주문 내역 리스트 가져오기
        Page<OrderDTO> order2 = orderRepository.findRangeDateOrder(dateRange.start, dateRange.end, pageable);
        log.info("order2 = {}", order2);
        return OrderListResponse.of(order2);
    }

}
