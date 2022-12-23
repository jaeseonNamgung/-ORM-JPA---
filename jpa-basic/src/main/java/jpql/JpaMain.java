package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args){

        EntityManagerFactory ef =
                Persistence.createEntityManagerFactory("hello");

        EntityManager em = ef.createEntityManager();
        EntityTransaction et=  em.getTransaction();

        try {


            et.commit();

        }catch (Exception e){
            et.rollback();
        }
        finally {
            em.close();
        }
        ef.close();
    }
}
