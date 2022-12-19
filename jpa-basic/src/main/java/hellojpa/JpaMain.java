package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            Team team = new Team();
            team.setName("team");
            entityManager.persist(team);

            Member member = new Member();
            member.setUsername("user1");
            member.setTeam(team);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            // Member와 Team이 조인 돼서 실행.
            Member findMember = entityManager.find(Member.class, member.getId());
            // 즉시 로딩을 사용할 경우 team은 프록시 객체가 아니라는걸 확인 가능
            System.out.println("team: " + findMember.getTeam().getClass());

            // 즉시 로딩을 사용할 경우 이미 Member와 Team이 한번에 조인 쿼리로 실행 됐기
            // 때문에 Team 쿼리는 실행 될 필요가 없어 실행 되지 않는다.
            System.out.println("==================");
            findMember.getTeam().getName();
            System.out.println("==================");

            entityTransaction.commit();
        }catch (Exception e){
            entityTransaction.rollback();
            e.printStackTrace();
        }finally{
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
