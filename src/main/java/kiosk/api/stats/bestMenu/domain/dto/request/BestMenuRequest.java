package kiosk.api.stats.bestMenu.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import kiosk.api.stats.common.DateType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BestMenuRequest {

    @NotNull(message = "조회할 날짜는 필수입니다.")
    @PastOrPresent(message = "조회할 날짜는 미래일 수 없습니다.")
    private LocalDate date;

    @NotNull(message = "날짜 타입 카테고리는 필수입니다.")
    private DateType dateType;
}
