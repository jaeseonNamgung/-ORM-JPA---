package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){

        EntityManagerFactory ef =
                Persistence.createEntityManagerFactory("hello");

        EntityManager em = ef.createEntityManager();
        EntityTransaction et=  em.getTransaction();
        et.begin();

        try {

            Member member = new Member();
            member.setUsername("member");
            member.setAge(26);
            em.persist(member);

            em.flush();
            em.clear();

            // 프로젝션 - 여러 값 조회
            // 1번째 방법
            List resultList =
                    em.createQuery("select m.username, m.age from Member m")
                                    .getResultList();
            Object o = resultList.get(0);
            Object[] result = (Object[]) o;
            System.out.println("username = " + result[0]);
            System.out.println("age = " + result[1]);

            // 2번째 방법
            List<Object[]> resultList2 =
                    em.createQuery("select m.username, m.age from Member m")
                            .getResultList();
            Object[] result1 = resultList2.get(0);

            // 3번째 방법
            // MemberDTO를 이용한 값 조회
            em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m ", MemberDTO.class)
                            .getResultList();




            et.commit();

        }catch (Exception e){
            et.rollback();
            e.printStackTrace();
        }
        finally {
            em.close();
        }
        ef.close();
    }
}
