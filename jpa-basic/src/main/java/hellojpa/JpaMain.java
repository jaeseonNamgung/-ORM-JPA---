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

            Address address = new Address("city", "street", "zipcode");

            Member member1 =  new Member();
            member1.setHomeAddress(address);

            Member member2 =  new Member();
            member2.setHomeAddress(address);
            // 같은 메모리를 참조하는 객체는 하나의 객체가 값을 변경해도
            // 같은 메모리를 참조하는 모든 객체가 값이 변경되는 SideEffect가
            // 발생하게 된다.
            member2.getHomeAddress().setCity("city2");
            entityManager.persist(member1);
            entityManager.persist(member2);


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
