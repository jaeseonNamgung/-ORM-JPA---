package hellojpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// @MappedSuperclass: 공통적 사용되는 컬럼이 있을 때 사용
// 하나의 클래스에 공통으로 사용되는 컬럼을 정의한 후 @MappedSuperclass 어노테이션을
// 클래스 위에 붙여주고 사용하려는 클래스에 공통으로 정의된 클래스를 상속 받으면 된다.
// @MappedSuperclass 사용할 경우 직접 사용할 일이 없기 때문에 추상클래스를 사용하길 권장
@MappedSuperclass
public class BaseEntity {

    @Column(name = "INSERT_MEMBER")
    private String createdBy;
    @Column(name = "UPDATE_MEMBER")
    private LocalDateTime createDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
