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

           Member findMember = em.createNamedQuery("Member.findByUsername", Member.class)
                           .setParameter("username", member.getUsername())
                           .getSingleResult();
            System.out.println("result: " + findMember);



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
