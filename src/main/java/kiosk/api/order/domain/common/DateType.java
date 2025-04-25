package kiosk.api.order.domain.common;

import kiosk.api.stats.bestMenu.domain.dto.request.DateRange;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public enum DateType {

    DAILY {
        @Override
        public LocalDateTime getStartDateTime(LocalDate date) {
            return date.atStartOfDay();
        }

        @Override
        public LocalDateTime getEndDateTime(LocalDate date) {
            return date.plusDays(1).atStartOfDay();
        }
    },
    WEEKLY {
        @Override
        public LocalDateTime getStartDateTime(LocalDate date) {
            return date.with(DayOfWeek.MONDAY).atStartOfDay();
        }

        @Override
        public LocalDateTime getEndDateTime(LocalDate date) {
            return date.with(DayOfWeek.MONDAY).plusWeeks(1).atStartOfDay();
        }
    },
    MONTHLY {
        @Override
        public LocalDateTime getStartDateTime(LocalDate date) {
            return date.withDayOfMonth(1).atStartOfDay();
        }

        @Override
        public LocalDateTime getEndDateTime(LocalDate date) {
            return date.plusMonths(1).withDayOfMonth(1).atStartOfDay();
        }
    };

    public abstract LocalDateTime getStartDateTime(LocalDate date);
    public abstract LocalDateTime getEndDateTime(LocalDate date);

    public DateRange getDateRange(LocalDate date) {
        return new DateRange(getStartDateTime(date), getEndDateTime(date));
    }
}
