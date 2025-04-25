package kiosk.api.stats.bestMenu.service;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.repository.OrderDetailRepository;
import kiosk.api.stats.bestMenu.domain.dto.response.BestSellingMenuResponse;
import kiosk.api.stats.bestMenu.domain.dto.request.DateRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BestMenuServiceImpl implements BestMenuService {

    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<BestSellingMenuResponse> dailyBestMenu(OrderDateRequest request) {
        DateRange range = request.getDateType().getDateRange(request.getDate());

        return orderDetailRepository.DailyBestMenuResult(range.start, range.end);
    }

}
