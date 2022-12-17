package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {

            Movie movie = new Movie();
            movie.setDirector("aaa");
            movie.setActor("bbb");
            movie.setName("바람과함께사라지다.");
            movie.setPrice(10000);

            entityManager.persist(movie);

            entityManager.flush();
            entityManager.clear();

            Movie findMovie = entityManager.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);

            entityTransaction.commit();
        }catch (Exception e){
            entityTransaction.rollback();
        }finally{
            entityManager.close();
        }
        entityManagerFactory.close();
    }
}
