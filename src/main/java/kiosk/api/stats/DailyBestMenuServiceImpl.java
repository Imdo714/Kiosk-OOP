package kiosk.api.stats;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.internal.OrderDTO;
import kiosk.api.order.repository.OrderDetailRepository;
import kiosk.api.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DailyBestMenuServiceImpl implements DailyBestMenuService{

    private final OrderDetailRepository orderDetailRepository;

    @Override
    public void dailyBestMenu(OrderDateRequest request) {
        LocalDateTime startDateTime = request.getDate().atStartOfDay();
        LocalDateTime endDateTime = request.getDate().plusDays(1).atStartOfDay();

        log.info("startDateTime = {}", startDateTime);
        log.info("endDateTime = {}", endDateTime);

        List<BestSellingMenuDto> res = orderDetailRepository.DailyBestMenuResult(startDateTime, endDateTime);
        BestSellingMenuDto bestMenu = res.stream().findFirst().orElse(null);

        log.info("res = {}", res);
        log.info("bestMenu = {}", bestMenu);
    }

}
