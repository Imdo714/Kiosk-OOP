package kiosk.api.stats;

import jakarta.validation.Valid;
import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MenuStatsController {

    private final DailyBestMenuService dailyBestMenuService;

    @GetMapping("/menu/best/daily")
    public void dailyBestMenu(@Valid @RequestBody OrderDateRequest request){
        dailyBestMenuService.dailyBestMenu(request);
    }

}