package kiosk.api.order.domain.dto.response;

import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Builder
public class OrderDateTotalResponse {

    private int totalPrice;
    private int totalQuantity;
    private LocalDate date;

    public OrderDateTotalResponse(int orderPrice, int orderQuantity, LocalDate createdAt) {
        this.totalPrice = orderPrice;
        this.totalQuantity = orderQuantity;
        this.date = createdAt;
    }
}
