package kiosk.api.order.service.orderQueryDate.weekly;

import kiosk.api.order.domain.dto.request.dateTimeRequest.OrderDateRequest;
import kiosk.api.order.domain.dto.response.OrderDateTotalResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeeklyOrderQueryServiceImplTest {

    @Autowired
    private WeeklyOrderQueryServiceImpl weeklyOrderQueryService;

    @DisplayName("주간 첫 월요일 날짜 반환")
    @Test
    void getStartDateTime() {
        // given
        LocalDate testDate = LocalDate.of(2025, 4, 17);
        LocalDateTime result = weeklyOrderQueryService.getStartDateTime(testDate);

        // when // then
        assertThat(result).isEqualToIgnoringNanos(LocalDateTime.of(2025, 4, 14, 0, 0));
    }

    @DisplayName("주간 다음 월요일 날짜 반환")
    @Test
    void getEndDateTime() {
        // given
        LocalDate testDate = LocalDate.of(2025, 4, 17);
        LocalDateTime result = weeklyOrderQueryService.getEndDateTime(testDate);

        // when // then
        assertThat(result).isEqualToIgnoringNanos(LocalDateTime.of(2025, 4, 21, 0, 0));
    }

}