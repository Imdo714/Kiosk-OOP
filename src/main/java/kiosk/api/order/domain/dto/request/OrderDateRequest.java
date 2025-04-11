package kiosk.api.order.domain.dto.request;

import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDateRequest {

    private LocalDate createdAt;

}
