package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {

            Address address = new Address("city", "street", "zipcode");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressHistory().add(new Address("old1", "street", "zipcode"));
            member.getAddressHistory().add(new Address("old2", "street", "zipcode"));
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            // 조회
            // member 만 조회 할 경우 컬렉션 값 타입은 조회 되지 않는다.
            // 왜냐하면 컬렉션 값 타입은 지연 로딩이 기본 값이기 때문이다.
            System.out.println("==========조회==============");
            Member findMember = entityManager.find(Member.class, member.getId());

            // 컬렉션을 조회 했을 때 비로서 컬렉션 테이블도 select 된다.
            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address value : addressHistory) {
                System.out.println("getCity = " + value.getCity());
                System.out.println("getStreet = " + value.getStreet());
                System.out.println("getZipcode = " + value.getZipcode());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String value: favoriteFoods) {
                System.out.println("favoriteFoods = " + value);
            }




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
