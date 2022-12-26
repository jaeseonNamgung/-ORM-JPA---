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

            for(int i = 1; i <= 100; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            List<Member> resultList =
                    em.createQuery("select m from Member m order by m.age desc", Member.class)
                            .setFirstResult(1)
                            .setMaxResults(10)
                            .getResultList();


            System.out.println("result.size = " + resultList.size());
            for (Member m: resultList) {
                System.out.println("result = " + m);
            }

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
