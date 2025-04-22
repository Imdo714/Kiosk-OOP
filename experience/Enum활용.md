## 🔍 Enum을 활용한 날짜 계산 다형성 적용

- [다양성_ISP 개선](experience/다향성_ISP.md)
> 이 문서는 일일, 주간, 월간 총 매출 및 총 주문 수량 조회 기능을 다형성과 ISP 원칙을 활용해 개선한 내용을 담고 있습니다.
---

## ✨기능 요구사항
- 이번 기능으로 일일, 주간, 월간 베스트 메뉴를 조회하는 기능을 요청받았습니다.
- 기존에도 날짜 계산 코드가 존재했지만, 이를 더 효율적으로 재사용하기 위해 구조 개선이 필요했습니다.
- 기존에는 클래스 마다 날짜 계산을 해주어야 하는 방식이여서 다른 곳에서 날짜가 필요하면 재 사용을 못하였습니다.

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
---

## ✅ 개선 방향
- 날짜 계산 로직을 공통화하고 재사용성을 높이기 위해 `DateType`이라는 Enum 클래스를 설계했습니다.
- 이제 각 클래스마다 `getStartDateTime`, `getEndDateTime`을 구현할 필요 없이, `DateType Enum`을 통해 통일된 방식으로 날짜 범위를 가져올 수 있습니다.

```java
DateRange range = DateType.DAILY.getDateRange(request.getDate());
```

---

## 🔧 적용 설계 요약
- Enum 내부에서 메서드 오버라이딩을 통해 다형성을 구현했습니다.
- 각 `DateType(DAILY, WEEKLY, MONTHLY)`이 스스로 날짜 계산 책임을 가지도록 하여 OCP(개방-폐쇄 원칙) 을 만족합니다.
- 유지보수가 쉬우며, 향후 분기별, 연도별, 사용자 지정(Custom) 등 새로운 타입을 확장할 때도 Enum만 수정하면 됩니다.

```java
public enum DateType {

    DAILY {
        @Override
        public LocalDateTime getStartDateTime(LocalDate date) {
            return date.atStartOfDay();
        }

        @Override
        public LocalDateTime getEndDateTime(LocalDate date) {
            return date.plusDays(1).atStartOfDay();
        }
    },
    WEEKLY {
        @Override
        public LocalDateTime getStartDateTime(LocalDate date) {
            return date.with(DayOfWeek.MONDAY).atStartOfDay();
        }

        @Override
        public LocalDateTime getEndDateTime(LocalDate date) {
            return date.with(DayOfWeek.MONDAY).plusWeeks(1).atStartOfDay();
        }
    },
    MONTHLY {
        @Override
        public LocalDateTime getStartDateTime(LocalDate date) {
            return date.withDayOfMonth(1).atStartOfDay();
        }

        @Override
        public LocalDateTime getEndDateTime(LocalDate date) {
            return date.plusMonths(1).withDayOfMonth(1).atStartOfDay();
        }
    };

    public abstract LocalDateTime getStartDateTime(LocalDate date);
    public abstract LocalDateTime getEndDateTime(LocalDate date);

    public DateRange getDateRange(LocalDate date) {
        return new DateRange(getStartDateTime(date), getEndDateTime(date));
    }
}
```
---

## 💡Enum 을 활용한 장점

- ### 증복 제거
  - 클래스마다 날짜 계산 메서드를 증복 작성하지 않아도 됩니다.
- ### 코드 일관성 향상
  - 날짜 계산 로직이 하나의 Enum으로 집약되어 일관성이 향상됩니다.
- ### 확장성 강화
  - 새로운 날짜 단위가 추가될 때도 기존 코드는 수정 하지 않아 확장성에서도 향상됩니다.
