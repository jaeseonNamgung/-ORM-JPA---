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

            // set 메소드를 private 으로 지정하면
            // 다른 클래스에서 set 메소드를 샤용할 수 없다.
            // member2.getHomeAddress().setCity("city2");
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
