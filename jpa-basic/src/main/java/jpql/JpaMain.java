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

            TypedQuery<Member> query =  em.createQuery("select m from Member m", Member.class);

            List<Member> result =  query.getResultList();

            // getSingleResult는 조회한 결과 값이 하나일 때 사용
            // null 값이 거나 둘 이상일 때 Exception이 발생한다.
            Member singleMember = query.getSingleResult();

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
