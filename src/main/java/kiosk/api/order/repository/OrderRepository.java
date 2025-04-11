package kiosk.api.order.repository;

import kiosk.api.order.domain.OrderEntity;
import kiosk.api.order.domain.request.OrderDTO;
import kiosk.api.order.domain.request.OrderDateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT new kiosk.api.order.domain.request.OrderDTO(o.orderId, o.orderPrice, o.orderQuantity, o.orderDate) FROM OrderEntity o WHERE o.orderDate >= :start AND o.orderDate < :end")
    List<OrderDTO> findDailyOrder(LocalDateTime start, LocalDateTime end);

}
