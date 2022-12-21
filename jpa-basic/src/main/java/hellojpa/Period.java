package hellojpa;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

// 임베디드 타입 구현 (임베디드 타입 구현 클래스에 메소드 사용도 가능)
// 기본 생성자 필수
@Embeddable
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Period(){}

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

}
