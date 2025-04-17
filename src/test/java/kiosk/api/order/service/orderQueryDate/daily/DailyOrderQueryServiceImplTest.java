package kiosk.api.order.service.orderQueryDate.daily;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DailyOrderQueryServiceImplTest {

    @Autowired
    private DailyOrderQueryService dailyOrderQueryService;

    @Autowired
    private DailyOrderQueryServiceImpl dailyOrderQueryServiceImpl;

    @DisplayName("일일 총 주문 수량, 총 금액 조회")
    @Test
    void getDailyOrder() {
        // given
        OrderDateRequest request = new OrderDateRequest(LocalDate.of(2024, 4, 17));

        // when
        OrderDateTotalResponse dailyOrder = dailyOrderQueryService.getDailyOrder(request);

        // then
        assertThat(dailyOrder).isNotNull();
    }

    @DisplayName("일일 시작 날짜 검증, 일일 자정을 반환")
    @Test
    void getStartDateTime() {
        // given
        LocalDate testDate = LocalDate.of(2025, 4, 17);
        LocalDateTime result = dailyOrderQueryServiceImpl.getStartDateTime(testDate);

        // when // then
        assertThat(result).isEqualToIgnoringNanos(LocalDateTime.of(2025, 4, 17, 0, 0));
    }

    @DisplayName("일일 마지막 날짜 검증, 다음 날 자정을 반환")
    @Test
    void getEndDateTime() {
        // given
        LocalDate testDate = LocalDate.of(2025, 4, 17);
        LocalDateTime result = dailyOrderQueryServiceImpl.getEndDateTime(testDate);

        // when // then
        assertThat(result).isEqualToIgnoringNanos(LocalDateTime.of(2025, 4, 18, 0, 0));
    }

}