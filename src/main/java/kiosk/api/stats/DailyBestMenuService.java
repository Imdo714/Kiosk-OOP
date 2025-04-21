package kiosk.api.stats;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;

public interface DailyBestMenuService {
    void dailyBestMenu(OrderDateRequest request);
}
