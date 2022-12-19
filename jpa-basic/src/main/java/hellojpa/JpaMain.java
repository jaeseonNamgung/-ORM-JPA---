package hellojpa;

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
            Member member = new Member();
            member.setUsername("user1");
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            // 프록시 객체 초기화
            // 프록시 객체를 초기화 할 때는 쿼리문을 날리지 않는다.
            Member findMember = entityManager.getReference(Member.class, member.getId());

            // findMember.getUsername() 처럼 값을 꺼낼때 영속성 컨테이너를 통해 DB와 연결되어
            // 쿼리문을 통해 값을 얻어 올 수 있다.
            System.out.println("findMember.getName = " + findMember.getUsername());


            entityTransaction.commit();
        }catch (Exception e){
            entityTransaction.rollback();
        }finally{
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
