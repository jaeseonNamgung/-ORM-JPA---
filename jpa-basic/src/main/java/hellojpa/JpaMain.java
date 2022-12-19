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
            Member member = new Member();
            member.setUsername("user1");
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            Member reMember = entityManager.getReference(Member.class, member.getId());
            
            // 프록시 인스턴스 초기화 여부 확인
            // 지금 실행 할 경우 프록시가 초기화 되어 있지 않기 때문에 false를 리턴
            System.out.println("isLoaded : " + entityManagerFactory
                    .getPersistenceUnitUtil().isLoaded(reMember));

            // 프록시 클래스 확인 방법
            System.out.println("getClass() : " + reMember.getClass());
            
            // 프록시 강제 초기화 : hibernate에서 제공하는 기능
            Hibernate.initialize(reMember);
            
            System.out.println("프록시 강제 초기화 후 isLoaded : " + entityManagerFactory
                    .getPersistenceUnitUtil().isLoaded(reMember));

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
