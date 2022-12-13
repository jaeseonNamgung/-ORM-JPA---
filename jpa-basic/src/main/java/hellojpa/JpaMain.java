package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args){
        // EntityManagerFactory 는 웹 서버에 올라오는 시점에
        // (DB당) 하나만 생성해서 애플리케이션 전체에서 공유한다.
        EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("hello");
        // EntityManager 은 고객에 요청이 올 때마다 계속 사용 됐다가 종료 된다.
        // EntityManager 은 쓰레드간에 공유가 없다 (사용하고 버려야 한다.)
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행 해야 한다. 중요!!!
        try {
            Member findMember = entityManager.find(Member.class, 1L);
            findMember.setName("HelloJPA");

            // JPA 엔티티를 통해서 값을 가져오면 그 값은 JPA가 관리하게 된다.
            // JPA 는 트랜잭션 커밋하는 시점에 변경이 됐는지 안 됐는지를 체크 한다
            // 만약 변경이 됐다면 JPA는 자동으로 update 쿼리를 동작 시킨다. 그런 후 커밋하게 된다.
            // entityManager.persist(findMember);

            entityTransaction.commit();
        }catch (Exception e){
            entityTransaction.rollback();
        }finally{
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
