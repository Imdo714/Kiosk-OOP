package kiosk.api.order.service.orderGetDate.monthly;

import jakarta.validation.Valid;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.service.orderGetDate.OrderDateTimeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Slf4j
@RequiredArgsConstructor
@Service
public class MonthlyOrderQueryServiceImpl implements MonthlyOrderQueryService{

    private final OrderDateTimeGenerator orderDateTimeGenerator;

    @Override
    public OrderDateTotalResponse getMonthlyOrder(OrderDateRequest request) {
        YearMonth month = YearMonth.from(request.getDate());
        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end = month.plusMonths(1).atDay(1).atStartOfDay();

        log.info("start = {}", start);
        log.info("end = {}", end);

        return orderDateTimeGenerator.getOrderDailyResponse(start, end, request.getDate());
    }
}
