package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {

    @Id
    private Long id;

    // 컬럼 매핑
    @Column(name = "name")
    private String username;

    private int age;

    // enum 타입 매핑
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 날짜 타입 매핑
    @Temporal(TemporalType.TIMESTAMP)
    private Date createedDatee;

    // 날짜 타입 매핑
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModefiedDate;

    // @Lob 데이터에 큰 데이터를 주고 싶을 때 사용
    // BLOB, CLOB 미팽
    @Lob 
    private String description;

    // DB에 매핑하고 싶지 않고 메모리에서만 사용하고 싶을 떄 사용
    @Transient
    private int temp;
    public Member(){
        
    }
}
