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


            Member singleResult = em.createQuery("select m from Member m where m.username =:username", Member.class)
                    .setParameter("username", "member")
                    .getSingleResult();

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
