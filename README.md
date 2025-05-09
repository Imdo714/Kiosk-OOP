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

- [기능 종류 및 설명](experience/기능설명.md)
- [다양성_ISP 개선](experience/다향성_ISP.md)
- [N+1 쿼리 문제 개선](experience/JOIN%20FETCH.md)
- [Enum활용하여 다향성 개선](experience/Enum활용.md)

---
## 📘 ERD
> 공부하면서 기능이 추가 되어 ERD 변경이 자주 일어납니다.

![Image](https://github.com/user-attachments/assets/3ec4c058-0cfa-4a12-bb2a-0a06511e4cee)
