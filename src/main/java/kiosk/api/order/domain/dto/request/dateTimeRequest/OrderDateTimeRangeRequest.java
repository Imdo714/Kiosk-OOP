package kiosk.api.order.domain.dto.request.dateTimeRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDateTimeRangeRequest {

    @NotNull(message = "조회할 날짜는 필수입니다.")
    @PastOrPresent(message = "조회할 날짜는 미래일 수 없습니다.")
    private LocalDate date;

    @Valid // OrderTimeRange 내부 필드 검증 해줌
    @NotNull(message = "시간 범위는 필수입니다.")
    private OrderTimeRange orderTimeRange;

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(date, orderTimeRange.getStartTime());
    }

    public LocalDateTime getEndDateTime() {
        return LocalDateTime.of(date, orderTimeRange.getEndTime());
    }

}
