package kiosk.api.stats.bestMenu.service;

import kiosk.api.stats.bestMenu.domain.dto.request.BestMenuRequest;
import kiosk.api.stats.bestMenu.domain.dto.response.BestSellingMenuResponse;

import java.util.List;

public interface BestMenuService {
    List<BestSellingMenuResponse> dailyBestMenu(BestMenuRequest request);
}
