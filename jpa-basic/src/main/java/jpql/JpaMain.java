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

            Member member = new Member();
            member.setUsername("member");
            member.setAge(26);
            em.persist(member);

            // 엔티티 프로젝션
            List<Member> memberList = em.createQuery("select m from Member m", Member.class)
                    .getResultList();
            // 엔티티 프로젝션
            em.createQuery("select m.team from Member m", Member.class
            );
            // team을 member 에서 조회 할 때는
            // 위에 코드 보다 아래 코드처럼 join을 이용해서 조회해야 한다.
            em.createQuery("select t from Member m join Team t", Team.class);

            // 임베디드 타입 프로젝션
            em.createQuery("select o.address from Order o", Address.class);
            
            // 스칼라 타입 프로젝션
            em.createQuery("select distinct m.username, m.age from Member m");

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
