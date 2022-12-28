package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){

        EntityManagerFactory ef =
                Persistence.createEntityManagerFactory("hello");

        EntityManager em = ef.createEntityManager();
        EntityTransaction et=  em.getTransaction();
        et.begin();

        try {

            Team team = new Team();
            team.setName("team");

            Member member = new Member();
            member.setAge(26);
            member.setUsername("member");
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 상태 필드(state field): 단순히 값을 저장하기 위한 필드
            // 특징: 경로 탐색의 끝, 탐색 X
            String memberList =
                    em.createQuery("select m.username from Member m", String.class)
                                    .getSingleResult();
            System.out.println("m.username: " + memberList);
            // 단일 값 연관 필드:
            // @ManyToOne, @OneToOne, 대상이 엔티티(ex:m.team)
            // 특징: 묵시적 내부 조인(inner join) 발생, 탐색O
            // 예: m.team 에서 m.team.name으로 탐색 가능
            memberList =
                    em.createQuery("select m.team.name from Member m", String.class)
                            .getSingleResult();
            System.out.println("m.team.name: " + memberList);

            // 컬렉션 값 연관 필드:
            //@OneToMany, @ManyToMany, 대상이 컬렉션(ex:m.orders)
            //특징: 묵시적 내부 조인 발생, 탐색X (size 만 탐색 가능)
            // From 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
            Object o =
                    em.createQuery("select t.memberList from Team t", Object.class)
                            .getSingleResult();
            System.out.println("t.memberList: " + o);

            // From 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
            memberList =
                    em.createQuery("select m.username from Team t join t.memberList m", String.class)
                            .getSingleResult();
            System.out.println("t.memberList.username: " + memberList);


            et.commit();

        }catch (Exception e){
            et.rollback();
            e.printStackTrace();
        }
        finally {
            em.close();
        }
        ef.close();
    }
}
