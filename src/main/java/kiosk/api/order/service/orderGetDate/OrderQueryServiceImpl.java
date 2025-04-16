//package kiosk.api.order.service.orderGetDate;
//
//import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
//import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateTimeRangeRequest;
//import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class OrderQueryServiceImpl implements OrderQueryService {
//
//    private final OrderGetDateTimeGenerator orderGetDateTimeGenerator;
//
//    @Override
//    public OrderDateTotalResponse getDailyOrder(OrderDateRequest request) {
//        LocalDateTime start = request.getDate().atStartOfDay();
//        LocalDateTime end = request.getDate().plusDays(1).atStartOfDay();
//
//        return orderGetDateTimeGenerator.getOrderDailyResponse(start, end, request.getDate());
//    }
//
//    @Override
//    public OrderDateTotalResponse getDailyTimeOrder(OrderDateTimeRangeRequest request) {
//        return orderGetDateTimeGenerator.getOrderDailyResponse(request.getStartDateTime(), request.getEndDateTime(), request.getDate());
//    }
//
//}
