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

            // member만 쿼리 실행된다.
            Member findMember = entityManager.find(Member.class, member.getId());
            // team은 프록시 객체라는걸 확인 가능
            System.out.println("team: " + findMember.getTeam().getClass());

            // team 을 조회할 경우 team은 프록시 객체이기 때문에 영속성 컨테이너의 도움으로
            // DB와 연결해서 값을 알아낼 수 있다.
            // 애플리케이션을 실행 할 경우 team 쿼리가 실행되는걸 확인 할 수 있다.
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
