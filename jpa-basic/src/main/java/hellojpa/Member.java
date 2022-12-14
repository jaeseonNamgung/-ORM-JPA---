package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {

    // 기본키 매핑
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 컬럼 매핑
    @Column(name = "name")
    private String username;
    
    public Member(){
        
    }
}
