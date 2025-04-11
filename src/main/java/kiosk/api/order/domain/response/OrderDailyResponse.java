package kiosk.api.order.domain.response;

import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@Builder
public class OrderDailyResponse {

    private int orderPrice;
    private int orderQuantity;
    private LocalDate createdAt;

    public OrderDailyResponse(int orderPrice, int orderQuantity, LocalDate createdAt) {
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
        this.createdAt = createdAt;
    }
}
