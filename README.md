# Kiosk-OOP

### ☕ Menu 도메인

- **MenuController**
  - 메뉴 등록, 메뉴 수정, 조회 요청을 처리
- **MenuService / MenuServiceImpl**
  - 흐름 관리 및 비즈니스 로직 처리
- **MenuRepository / MenuQueryDsl**
  - DB 접근 및 동적 쿼리 구현
- **MenuEntity**
  - 메뉴 엔티티 정의
- **MenuCreateRequest / MenuUpdate**
  - 메뉴 생성/수정 요청 DTO
- **MenuListResponse / MenuResponse**
  - 메뉴 응답 DTO
---

### 🧾 Order 도메인

- **OrderController**
  - 주문 생성 및 일별 주문 조회 처리
- **OrderService / OrderServiceImpl**
  - 주문 흐름 관리 및 비즈니스 로직 처리
- **OrderRepository**
  - 주문 정보 저장 및 조회
- **OrderFactory**
  - 주문 생성 책임 분리
- **OrderEntity / OrderDetailEntity**
  - 주문 및 주문 상세 엔티티
- **OrderSummary**
  - 주문 총계 계산
- **OrderCreateRequest / OrderDailyResponse**
  - 주문 요청 및 응답 DTO

---

📌 *구현 포인트*  
- 객체지향(OOP) 기반으로 설계하고, SOLID 원칙을 적극 적용
- 내부 필드에 대한 직접 접근을 제한하고, 메서드를 통해서만 상태값 변경 허용
- `MenuQueryDsl`을 활용해 메뉴 조회 시 **동적 쿼리** 처리 (카테고리, 상태 등 조건 필터링)
- `Enum`을 통해 메뉴 상태 및 카테고리를 **타입 안정성 있게 관리**
- 주문 생성 시 `OrderFactory`를 통해 주문 엔티티 생성
- `OrderSummary`에서 전체 주문 수량 및 가격 계산 로직을 별도로 분리
- 날짜 및 시간에 따라 주문 정보를 조회하고 응답을 생성하는 로직을 `OrderDateTimeResponseGenerator`에 통합함으로써, 시간 처리 로직의 일관성과 재사용성을 높였습니다.
  
---
## 📘 클래스 다이어그램 (UML)
> 이 UML은 키오스크 시스템의 주요 클래스 구조를 나타냈습니다.

![Image](https://github.com/user-attachments/assets/d991dad1-9d81-44a3-bec5-edf4c93b6fe1)
![Image](https://github.com/user-attachments/assets/306ec177-cf9e-4cfe-b697-2275d2565f25)
![Image](https://github.com/user-attachments/assets/7830e69e-fc46-457a-a4d5-208fb43a1a3e)
