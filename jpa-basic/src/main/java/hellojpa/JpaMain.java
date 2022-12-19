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

            Member reMember = entityManager.getReference(Member.class, member.getId());
            // 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면
            // 문제 발생 예) entityManager.detach(), close(), clear()
            entityManager.detach(reMember); // 준영속 상태
            System.out.println("reMember.getUsername : " + reMember.getUsername());

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
