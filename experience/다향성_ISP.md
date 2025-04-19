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
## 📘 1차 시퀀스 다이어그램 (UML)
> 1차 구조 개선 시퀀스 다이어그램입니다. 확장성을 고려하지 않음

![Image](https://github.com/user-attachments/assets/ff5c1f4a-58db-4644-b8b1-1622be5692f8)

---
## 🔍 2차 구조 문제
- `OrderService`(주문 생성) 와 `OrderQueryService`(주문 조회) 를 잘 분리 함 즉, SRP를 잘 지킴
- 인터페이스로 추상화 잘 지킴 기존 `OrderQueryService`를 보면 동일한 관심사를 가짐
- 하지만 조회 조건이 다양해져서 여러 메서드가 추가 된다면 `OrderQueryService`에 조회 메서드를 계속 넣으면 **거대 인터페이스**가 될 위혐이 생김
```java
public interface OrderQueryService {
    // OrderQueryService 는 날짜 기반 조회라는 관심사를 가짐 즉, 추상화가 같음
    // 일일 조회
    OrderDateTotalResponse getDailyOrder(OrderDateRequest request);
    // 시간 대 별 조회
    OrderDateTotalResponse getDailyTimeOrder(OrderDateTimeRangeRequest request);
}
```

---
## 💊 2차 구조 개선

- 아직은 괜찮지만, 미래 확장성을 고려했을 때는 OrderQueryService가 너무 많은 조회 책임을 갖게 되지 않도록 인터페이스 분리
- 즉, 월간 인터페이스, 주간 인터페이스, 일일 인터페이스, 시간별 인터페이스로 나눔
```java
public interface DailyOrderQueryService { 
    // 일일 조회
    OrderDateTotalResponse getDailyOrder(OrderDateRequest request);
}
public interface TimeOrderQueryService {
    // 시간 대 별 조회
    OrderDateTotalResponse getDailyTimeOrder(OrderDateTimeRangeRequest request);
}
public interface WeeklyOrderQueryService {
    // 주간 조회
    OrderDateTotalResponse getWeeklyOrder(OrderDateRequest request);
}
public interface MonthlyOrderQueryService {
    // 월간 조회
    OrderDateTotalResponse getMonthlyOrder(OrderDateRequest request);
}
```

## 🧠 구현체 코드

- 서비스 단에서 복잡한 날짜 로직 분리 즉,
- 날짜 계산은 전부 서비스 단에서 끝내고, `OrderDateTimeGenerator`는 가공된 날짜만 받아서 처리.
- 이제 분기 조회 같은 날짜 조회 기능 추가 시 코드 수정 없이 분기 인터페이스 생성해서 구현체 하나만 생성하면 된다.

```java
public class DailyOrderQueryServiceImpl implements DailyOrderQueryService{
    
    private final OrderDateTimeGenerator orderDateTimeGenerator;

    @Override
    public OrderDateTotalResponse getDailyOrder(OrderDateRequest request) {
        LocalDateTime start = request.getDate().atStartOfDay();
        LocalDateTime end = request.getDate().plusDays(1).atStartOfDay();

        return orderDateTimeGenerator.getOrderDailyResponse(start, end, request.getDate());
    }
}

public class WeeklyOrderQueryServiceImpl implements WeeklyOrderQueryService {

    private final OrderDateTimeGenerator orderDateTimeGenerator;
  
    @Override
    public OrderDateTotalResponse getWeeklyOrder(OrderDateRequest request) {
        LocalDateTime startDate = request.getDate().with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(7);
    
        return orderDateTimeGenerator.getOrderDailyResponse(startDate, endDate, request.getDate());
  }
}
```

---

## 📘 2차 시퀀스 다이어그램 (UML)
> 확장성을 고려해 책임 분리 하였습니다.

![Image](https://github.com/user-attachments/assets/c40a3d78-7e9b-42c7-ba06-fc349a3e09cc)

---

## 📢 3차 개선 증복이 많음

- 구현체 서비스 들을 보면 날짜 계산 후 `OrderDateTimeGenerator`를 호출하는 증복 코드가 발생
- **OOP(객체지향)** 을 살리기 위해, 변하지 않는 공통 로직은 추상 클래스에, 변화되는 날짜 계산 로직은 하위 클래스에서 오버라이딩 하도록 구조 개선.
- `orderDateTimeGenerator` 호출은 공통 로직이므로 추상 클래스에 위치
- 각 서비스 구현체에서는 날짜 계산에만 집중할 수 있도록 분리함

```java
public abstract class AbstractOrderQueryService {

    protected final OrderDateTimeGenerator orderDateTimeGenerator;

    protected AbstractOrderQueryService(OrderDateTimeGenerator orderDateTimeGenerator) {
        this.orderDateTimeGenerator = orderDateTimeGenerator;
    }

    protected abstract LocalDateTime getStartDateTime(LocalDate date);
    protected abstract LocalDateTime getEndDateTime(LocalDate date);

    // 기존 OrderDateTimeGenerator 호출 공통 처리 로직
    protected OrderDateTotalResponse getOrderResponse(OrderDateRequest request) {
        LocalDateTime start = getStartDateTime(request.getDate());
        LocalDateTime end = getEndDateTime(request.getDate());

        return orderDateTimeGenerator.getOrderDailyResponse(start, end, request.getDate());
    }
}
```

##	📆 구현체에서 날짜 계산 오버라이딩

- `AbstractOrderQueryService` 추상 클래를 상속해서 날짜 계산 로직을 계산
- `getDailyOrder` 메서드는 `DailyOrderQueryService` 인테페이스에서 받아 추상 클래스의 `getOrderResponse`메서드를 호출
- 즉, 외부에서는 `getDailyOrder()`호출 하게 되면 내부적으로는 추상 클래스의 `getOrderResponse()` 공통 로직을 실행 
  - 이 안에서 `getStartDateTime() / getEndDateTime()`을 호출해서 날짜 계산하고
  - 계산된 날짜로 `OrderDateTimeGenerator`를 호출해서 응답을 만듭니다.

```java
public class DailyOrderQueryServiceImpl extends AbstractOrderQueryService implements DailyOrderQueryService{

    protected DailyOrderQueryServiceImpl(OrderDateTimeGenerator orderDateTimeGenerator) {
        super(orderDateTimeGenerator);
    }

    @Override
    protected LocalDateTime getStartDateTime(LocalDate date) {
        return date.atStartOfDay();
    }

    @Override
    protected LocalDateTime getEndDateTime(LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }

    @Override
    public OrderDateTotalResponse getDailyOrder(OrderDateRequest request) {
        return getOrderResponse(request);
    }
}
```

## 📘 3차 시퀀스 다이어그램 (UML)
> 증복 코드를 제거하기 위해 추상 클래스를 사용하였습니다.

![Image](https://github.com/user-attachments/assets/751a16ba-feec-4542-9065-4a73093f684d)

---
## 📒 참고 자료
- https://www.baeldung.com/java-interface-segregation
- https://stackify.com/interface-segregation-principle/

## 최종 Class Diagram
![Image](https://github.com/user-attachments/assets/d9490997-5e88-4aa1-8f76-de8a550b894c)
