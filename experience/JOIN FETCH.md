## ⚡ JOIN FETCH를 사용하는 이유

---

### 📌 문제 상황: N+1 쿼리 문제 발생

- MenuEntity는 @OneToMany로 DiscountEntity와 연관돼 있으며, 기본적으로 LAZY 로딩입니다.
- 즉, 메뉴를 조회할 때 **`discountEntity`에 접근할 때마다** 별도의 쿼리가 실행
- 메뉴가 3개일 경우: MenuEntity 1번 + DiscountEntity 3번 → 총 4개의 쿼리 (N+1 문제)

```java
for (OrderDetailRequest detail : request.getOrderDetails()) {
    // MenuEntity와 연결된 DiscountEntity를 가져옴
    MenuEntity menu = menuService.findByIdWithDiscount(detail.getMenuId());

    menu.getOrderValid(); // 내부에서 할인 정보 접근
    order.addOrderDetail(menu, detail.getOrderQuantity());
}
```
---
### ✅ 해결 방법 : JOIN FETCH 사용

- `JOIN FETCH`를 사용하면 **연관된 엔티티를 한 번의 쿼리로 모두 조회**할 수 있어 성능을 크게 개선할 수 있습니다.
- 메뉴 3개가 있어도 쿼리 3번 → 1번으로 최적화됩니다.
- JOIN FETCH를 통해 불필요한 추가 쿼리 실행을 방지하고 성능을 최적화 할 수 있습니다.
- 이제 MenuEntity를 조회할 때 DiscountEntity를 의미 없는 조회 안하고 1번만 조회 할 수 있어 쿼리를 최소한 하였습니다.

```mysql
@Query("SELECT m FROM MenuEntity m LEFT JOIN FETCH m.discountEntity d WHERE m.menuId = :menuId")
MenuEntity findByIdWithDiscount(@Param("menuId") Long menuId);
```
---
### ⚠ JOIN FETCH 사용 시 주의할 점

- 페이징 처리와의 충돌
  - 페이징 처리 결과를 메모리로 모두 가져오기 때문에, 페이징을 적용하려면 메모리 부하가 커지고, 큰 데이터셋을 처리할 때 성능이 떨어질 수 있습니다.
- 연관 관계의 순환 참조 문제
  - 양방향 관계에서 JOIN FETCH를 사용하면 MenuEntity에서 **DiscountEntity**를 조인하고, DiscountEntity에서 다시 **MenuEntity**를 조인하는 무한 순환이 발생할 수 있습니다.

