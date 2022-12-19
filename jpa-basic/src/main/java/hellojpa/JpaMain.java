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

            // 값이 같은 이유
            // 1. 이미 영속성 컨테이너 1차 캐시에 값이 있기 때문에 불 필요하게 다른 곳에서
            // 값을 가져올 필요가 없음
            // 2. 같은 영속성 컨테이너에서 가져온 값은 항상 같은 타입을 보장 해줘야 한다.
            // 이게 JPA 동작하는 메커니즘이다.

            Member findMember = entityManager.find(Member.class, member.getId());
            System.out.println("findMember: " + findMember.getClass());

            Member reMember = entityManager.getReference(Member.class, member.getId());
            System.out.println("reMember: " + reMember.getClass());

            // 반대로 프록시를 먼저 초기화 할 경우 두 객체는 모두 같은 프록시 타입이 된다.







            entityTransaction.commit();
        }catch (Exception e){
            entityTransaction.rollback();
        }finally{
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
