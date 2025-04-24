package kiosk.api.stats.salesReport.service;

import kiosk.api.stats.bestMenu.domain.dto.request.BestMenuRequest;
import kiosk.api.stats.salesReport.domain.response.OrderListResponse;
import org.springframework.data.domain.Pageable;

public interface SalesReportService {

    OrderListResponse getSalesReportByPeriod(BestMenuRequest request, Pageable pageable);

}
