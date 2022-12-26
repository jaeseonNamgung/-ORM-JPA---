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
            team.setName("member");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(26);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();


            // inner join : inner은 생략 가능
            List<Member> innerJoin = em.createQuery("select m from Member m inner join m.team t", Member.class)
                            .getResultList();

            // outer join
            List<Member> outerJoin = em.createQuery("select m from Member m left join m.team t", Member.class)
                            .getResultList();

            // 세타 조인
            List<Member> thetaJon = em.createQuery("select m from Member m, Team t where m.username = t.name")
                    .getResultList();

            // on 절 사용
            List<Member> on = em.createQuery("select m from Member m left join m.team t on t.name = 'teamA' ")
                    .getResultList();
            // on 절 사용 - 연관 관계 없는 엔티티 외부 조인
            List<Member> on2 = em.createQuery("select m from Member m left join Team t on m.username = t.name")
                            .getResultList();

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
