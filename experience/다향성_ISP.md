# Kiosk-OOP
## 🔍 기존 구조 문제

초기 구조에서는 다음과 같은 문제가 있었습니다:

- `OrderService` 하나에 **모든 기능이 집중**되어 있었음
- 하나의 인터페이스에서 **주문 생성 + 조회 + 통계 등 모든 메서드 포함**
- **로직은 외부 클래스에 위임**했지만, 서비스가 지나치게 **의존성 허브처럼 동작**
- 기능 확장 시 책임 구분이 애매해지고, 테스트/유지보수 비용 증가

---
## ✅ 구조 개선 방식

이러한 문제를 해결하기 위해 **기능 분리 및 설계 원칙 적용**을 진행했습니다.

- `OrderQueryService`를 **조회 전용 인터페이스**로 분리하여,
    - 날짜/시간 기반 조회는 `OrderQueryService` 인터페이스 에서 처리
    - 내부 구현체인 `OrderQueryServiceImpl`에서는 **입력 파라미터(날짜/시간) 검증과 흐름 제어만 담당**
    - 실제 계산 로직은 `OrderGetDateTimeGenerator` 인터페이스로 위임
- `OrderDateTimeGenerator`는 날짜/시간대에 따라 **총 주문 수량/금액을 계산하는 책임 전담**
- "클라이언트는 자신이 사용하지 않는 메서드에 의존하지 않아야 한다"
- 테스트 용도 Mock 등으로 유연하게 확장 가능

---

## 📘 시퀀스 다이어그램 (UML)
> 이 UML은 키오스크 시스템의 주문 클래스 구조를 나타냈습니다.

![Image](https://github.com/user-attachments/assets/ff5c1f4a-58db-4644-b8b1-1622be5692f8)
![Image](https://github.com/user-attachments/assets/7830e69e-fc46-457a-a4d5-208fb43a1a3e)
