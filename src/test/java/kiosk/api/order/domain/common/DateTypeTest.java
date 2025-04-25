package kiosk.api.order.domain.common;

import kiosk.api.stats.bestMenu.domain.dto.request.DateRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DateTypeTest {

    @DisplayName("dateType이 DAILY일 때 날짜 계산 검증")
    @Test
    void getDateDailyRange() {
        // given
        LocalDate date = LocalDate.of(2025, 4, 10);

        // when
        DateRange range = DateType.DAILY.getDateRange(date);

        // then
        assertThat(range.start).isEqualTo(LocalDateTime.of(2025, 4, 10, 0, 0));
        assertThat(range.end).isEqualTo(LocalDateTime.of(2025, 4, 11, 0, 0));
    }

    @DisplayName("dateType이 WEEKLY일 때 날짜 계산 검증")
    @Test
    void getDateWeeklyRange() {
        // given
        LocalDate date = LocalDate.of(2025, 4, 10);

        // when
        DateRange range = DateType.WEEKLY.getDateRange(date);

        // then
        assertThat(range.start).isEqualTo(LocalDateTime.of(2025, 4, 7, 0, 0));
        assertThat(range.end).isEqualTo(LocalDateTime.of(2025, 4, 14, 0, 0));
    }

    @DisplayName("dateType이 MONTHLY일 때 날짜 계산 검증")
    @Test
    void getDateMonthlyRange() {
        // given
        LocalDate date = LocalDate.of(2025, 4, 10);

        // when
        DateRange range = DateType.MONTHLY.getDateRange(date);

        // then
        assertThat(range.start).isEqualTo(LocalDateTime.of(2025, 4, 1, 0, 0));
        assertThat(range.end).isEqualTo(LocalDateTime.of(2025, 5, 1, 0, 0));
    }

}