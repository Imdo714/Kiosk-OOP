package kiosk.api.stats.bestMenu.domain.dto.request;

import java.time.LocalDateTime;

public class DateRange {

    public final LocalDateTime start;
    public final LocalDateTime end;

    public DateRange(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }
}
