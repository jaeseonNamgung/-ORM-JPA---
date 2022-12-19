package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // 1대N 양방향 연관관계 방법
    // fetch = FetchType.LAZY : 지연 로딩
    // Member와 Team 중에 Member만 90% 이상 사용할 경우 지연로딩을 사용
    // Team은 프록시 객체로 조회 되고 애플리케이션을 실행할 경우 Member 쿼리만 조회 되고
    // Team은 필요할 경우 쿼리가 실행된다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
