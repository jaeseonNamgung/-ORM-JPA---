package jpql;

import javax.persistence.*;

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

            // TypedQuery: 반환 타입이 명확할 때 사용
            TypedQuery<Member> query =  em.createQuery("select m from Member m", Member.class);

            // Query:  반환 타입이 명확하지 않을 때 사용
            Query query1 = em.createQuery("select m.username, m.age from Member m");

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
