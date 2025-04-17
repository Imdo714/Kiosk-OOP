package kiosk.api.order.service.orderQueryDate.monthly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MonthlyOrderQueryServiceImplTest {

    @Autowired
    private MonthlyOrderQueryServiceImpl monthlyOrderQueryService;

    @DisplayName("주간 첫 월요일 날짜 반환")
    @Test
    void getStartDateTime() {
        // given
        LocalDate testDate = LocalDate.of(2025, 4, 17);
        LocalDateTime result = monthlyOrderQueryService.getStartDateTime(testDate);

        // when // then
        assertThat(result).isEqualToIgnoringNanos(LocalDateTime.of(2025, 4, 1, 0, 0));
    }

    @DisplayName("주간 다음 월요일 날짜 반환")
    @Test
    void getEndDateTime() {
        // given
        LocalDate testDate = LocalDate.of(2025, 4, 17);
        LocalDateTime result = monthlyOrderQueryService.getEndDateTime(testDate);

        // when // then
        assertThat(result).isEqualToIgnoringNanos(LocalDateTime.of(2025, 5, 1, 0, 0));
    }

}