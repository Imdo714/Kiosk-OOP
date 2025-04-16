package kiosk.api.order.service.orderGetDate.weekly;

import jakarta.validation.Valid;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import kiosk.api.order.service.orderGetDate.OrderDateTimeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeeklyOrderQueryServiceImpl implements WeeklyOrderQueryService {

    private final OrderDateTimeGenerator orderDateTimeGenerator;

    @Override
    public OrderDateTotalResponse getWeeklyOrder(OrderDateRequest request) {
        // .with(DayOfWeek.MONDAY) == 해당 주의 월요일 찾기
        LocalDateTime startDate = request.getDate().with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(7);

        return orderDateTimeGenerator.getOrderDailyResponse(startDate, endDate, request.getDate());
    }
}
