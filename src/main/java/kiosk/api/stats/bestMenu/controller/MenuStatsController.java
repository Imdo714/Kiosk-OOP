package kiosk.api.stats.bestMenu.controller;

import jakarta.validation.Valid;
import kiosk.api.ApiResponse;
import kiosk.api.stats.bestMenu.domain.dto.request.BestMenuRequest;
import kiosk.api.stats.bestMenu.domain.dto.response.BestSellingMenuResponse;
import kiosk.api.stats.bestMenu.service.BestMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MenuStatsController {

    private final BestMenuService dailyBestMenuService;

    @GetMapping("/menu/best")
    public ApiResponse<List<BestSellingMenuResponse>> dailyBestMenu(@Valid @RequestBody BestMenuRequest request){
        return ApiResponse.ok(dailyBestMenuService.dailyBestMenu(request));
    }

}