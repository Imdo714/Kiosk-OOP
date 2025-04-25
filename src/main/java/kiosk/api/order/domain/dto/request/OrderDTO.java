package kiosk.api.order.domain.dto.request;

import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
public class OrderDTO {

    private Long orderId;
    private int orderPrice;
    private int orderQuantity;
    private LocalDateTime orderDate;

    public OrderDTO(Long orderId, int orderPrice, int orderQuantity, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
        this.orderDate = orderDate;
    }
}
