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

            Team teamA = new Team();
            teamA.setName("teamA");
            Team teamB = new Team();
            teamB.setName("teamB");

            Member memberA = new Member();
            memberA.setUsername("회원1");
            memberA.changeTeam(teamA);
            em.persist(memberA);
            Member memberB = new Member();
            memberB.setUsername("회원2");
            memberB.changeTeam(teamA);
            em.persist(memberB);
            Member memberC = new Member();
            memberC.setUsername("회원3");
            memberC.changeTeam(teamB);
            em.persist(memberC);

            em.flush();
            em.clear();

            // - 페치 조인 대상에는 별칭을 줄 수 없다. (페치 조인은 조인할 모든 값을 다 가져와야 된다.)
            // 예) select m from Member m join fetch m.team as t
            // - 둘 이상의 컬렉션은 페치 조인 할 수 없다.
            // - 컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResult)를 사용할 수 없다.
            //   1. 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능
            //   2. 하이버네이트는 경고 로그를 남기고 메모리에서 페이징(매우 위험)
            //      일대다 에서 페이징 API 사용 방법
            //          1. 다대일에서 페이징 API를 사용
            //          2. @BatchSize 어노테이션 사용 (일대일 연관 매핑된 클래스에서 사용 예) team class)
            //          글로버 셋팅으로 설정 가능 ( 더 많이 사용 )
            //          <property name="hibernate.default_batch_fetch_size" value="100"/>
            List<Team> teamList = em.createQuery("select t from Team t", Team.class)
                            .setFirstResult(0)
                            .setMaxResults(2)
                            .getResultList();
            for (Team t: teamList) {
                System.out.println("fetch join(collection) team: " + t.getName() + "|memberss: " +
                        t.getMemberList().size());
                for (Member m : t.getMemberList()){
                    System.out.println("member: " + m.getUsername());
                }
            }




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
