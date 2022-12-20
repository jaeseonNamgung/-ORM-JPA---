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

            Child child1 = new Child();
            child1.setName("child1");
            Child child2 = new Child();
            child2.setName("child2");

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);
            parent.setName("parent1");

            entityManager.persist(parent);

            entityManager.flush();
            entityManager.clear();

            Parent findParent = entityManager.find(Parent.class, parent.getId());
            // orphanRemoval = true 기능을 사용할 경우 부모 객체에서 자식 엔티티를 삭제 할 경우
            // 자동으로 DB에 관련된 부모 객체에서 삭제된 자식이 삭제된다.
            // delete from child where id = 부모 객체에서 삭제된 자식 ID
            findParent.getChildList().remove(0);

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
