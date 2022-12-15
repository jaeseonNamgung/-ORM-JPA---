package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            System.out.println("=======================");
            Team team = new Team();
            team.setName("team1");
            entityManager.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);
            entityManager.persist(member);
            System.out.println("=======================");
            entityManager.flush();
            entityManager.clear();

            Member findMember = entityManager.find(Member.class, member.getId());
            List<Member> members =  findMember.getTeam().getMembers();


            for(Member m : members){
                System.out.println("m = " + m.getUsername());
            }
            entityTransaction.commit();
        }catch (Exception e){
            entityTransaction.rollback();
        }finally{
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
