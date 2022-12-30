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
           member.setUsername("member");
           member.setAge(26);
           member.changeTeam(team);
           em.persist(member);

           // where m = :member 엔티티 직접 사용
            // where m.id = memberId 와 같다
            // 실행된 SQL : select m.* from Member m where m.id = ?

           Member findMember = em.createQuery("select m from Member m where m = :member", Member.class)
                           .setParameter("member", member)
                                   .getSingleResult();
            System.out.println("result: " + findMember);

            // 엔티티 직접 사용 - 외래 키 값
            findMember = em.createQuery("select m from Member m where m.team = :team", Member.class)
                            .setParameter("team", team)
                                    .getSingleResult();
            System.out.println("외래 키 값 result: " + findMember);

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
