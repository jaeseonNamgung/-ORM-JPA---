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
            // 비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            
            // 영속
            // persist() 는 DB에 저장 되는게 아니라 영속성 컨텍스트에 저장된다.
            // DB에 저장되는 시점은 commit 되는 시점이다.
            entityManager.persist(member);

            entityTransaction.commit();
            
        }catch (Exception e){
            entityTransaction.rollback();
        }finally{
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
