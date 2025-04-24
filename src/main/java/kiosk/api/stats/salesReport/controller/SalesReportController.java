package kiosk.api.stats.salesReport.controller;

import jakarta.validation.Valid;
import kiosk.api.stats.bestMenu.domain.dto.request.BestMenuRequest;
import kiosk.api.stats.salesReport.service.SalesReportService;
import kiosk.api.stats.salesReport.domain.response.OrderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SalesReportController {

    private final SalesReportService salesReportService;

    @GetMapping("/sales")
    public OrderListResponse getSalesReport(@Valid @RequestBody BestMenuRequest request, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size);
        return salesReportService.getSalesReportByPeriod(request, pageable);
    }

}
