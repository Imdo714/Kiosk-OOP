package kiosk.api.stats.bestMenu.service;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.stats.bestMenu.domain.dto.response.BestSellingMenuResponse;

import java.util.List;

public interface BestMenuService {
    List<BestSellingMenuResponse> dailyBestMenu(OrderDateRequest request);
}
