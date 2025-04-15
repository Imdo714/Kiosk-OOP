# Kiosk-OOP

## 📌 *구현 포인트*  
- 객체지향(OOP) 기반으로 설계하고, SOLID 원칙을 적극 적용
- **단일 책임 원칙(SRP)** 에 따라 클래스는 하나의 책임만 가짐
- **인터페이스 분리 원칙(ISP)** 에 따라 역할에 따라 인터페이스를 세분화
- **개방-폐쇄 원칙(OCP)** 내부 필드에 대한 직접 접근을 제한하고, 메서드를 통해서만 상태값 변경 허용
- **의존 역전 원칙(DIP)** 에 따라 고수준 모듈이 저수준 모듈에 의존하지 않기
- `MenuQueryDsl`을 활용해 메뉴 조회 시 **동적 쿼리** 처리 (카테고리, 상태 등 조건 필터링)
- 서비스 구현체는 흐름만 관리하고 세부 로직은 다른 컴포넌트에 위임 → 각 책임이 작고 명확함
- 향후 확장(예: 월별 주문 조회, 카테고리별 통계 등)을 고려해 인터페이스 기반 확장성 확보
---
## 🔧 *개선 사항*

- [다양성_ISP 개선](experience/다향성_ISP.md)
- [N+1 쿼리 문제 개선](experience/JOIN%20FETCH.md)

---
## 📘 클래스 다이어그램 (UML)
> 이 UML은 키오스크 시스템의 주요 클래스 구조를 나타냈습니다.

![Image](https://github.com/user-attachments/assets/d991dad1-9d81-44a3-bec5-edf4c93b6fe1)
![Image](https://github.com/user-attachments/assets/306ec177-cf9e-4cfe-b697-2275d2565f25)
![Image](https://github.com/user-attachments/assets/7830e69e-fc46-457a-a4d5-208fb43a1a3e)
