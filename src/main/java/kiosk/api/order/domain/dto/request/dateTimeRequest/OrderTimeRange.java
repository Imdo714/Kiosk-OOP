package kiosk.api.order.domain.dto.request.dateTimeRequest;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderTimeRange {

    @NotNull(message = "시작 시간은 필수입니다.")
    private LocalTime startTime;

    @NotNull(message = "종료 시간은 필수입니다.")
    private LocalTime endTime;

    @AssertTrue(message = "종료 시간은 시작 시간 이후여야 합니다.")
    public boolean isValidRange() {
        return startTime != null && endTime != null && endTime.isAfter(startTime);
    }

}
