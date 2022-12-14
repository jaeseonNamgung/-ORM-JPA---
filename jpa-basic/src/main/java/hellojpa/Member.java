package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
@SequenceGenerator(
        name = "member_seq_generator",
        sequenceName = "member_seq"
)
public class Member {

    // 기본키 매핑
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    private Long id;

    // 컬럼 매핑
    @Column(name = "name")
    private String username;
    
    public Member(){
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
