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

            // 일반 조회를 했을 경우 - 지연 로딩인 LAZY를 설정 했기 때문에
            // 쿼리를 실행할 경우 member만 조회되고 team은 프록시 객체로써
            // m.getTeam()으로 호출할 때 쿼리가 실행된다.
            // 즉 최악의 경우 N + 1이 발생
            // 인덱스 페치 조인
            List<Member> memberList = em.createQuery("select m from Member m", Member.class)
                            .getResultList();
            for (Member m: memberList) {
                System.out.println("memebr: " + m.getUsername() + "|team: " + m.getTeam().getName());
                // memebr: 회원1|team: teamA (영속컨테이너)
                // memebr: 회원2|team: teamA (캐시)
                // memebr: 회원3|team: teamB (영속컨테이너)
            }
            // N + 1 문제를 해결하기 위한 방법
            // 페치 조인(fetch join)
            // 페지 조인을 사용할 경우 즉시 로딩처럼 쿼리를 한번에 실행 되기 때문에 N + 1 문제를 해결할 수 있다.
            memberList = em.createQuery("select m from Member m join fetch m.team", Member.class)
                            .getResultList();
            for (Member m: memberList) {
                System.out.println("fetch join(index) memebr: " + m.getUsername() + "|team: " + m.getTeam().getName());
            }

            // 컬렉션 페치 조인
            List<Team> teamList = em.createQuery("select t from Team t join fetch t.memberList", Team.class)
                            .getResultList();
            for (Team t: teamList) {
                System.out.println("fetch join(collection) team: " + t.getName() + "|memberss: " +
                        t.getMemberList().size());
                for (Member m : t.getMemberList()){
                    System.out.println("member: " + m.getUsername());
                }
            }
            
            
            // 중복 제거 방법 (페치 조인에 뻥튀기를 막기 위한 방법)
            // - SQL의 DISTINCT는 중복된 결과를 제거하는 명령
            // - JPQL의 DISTINCT 2가지 기능 제공
            // 1. SQL에 DISTINCT를 추가
            // 2. 애플리케이션에서 엔티티 중복 제거 (같은 식별자를 가진 team 엔티티 제거)

            teamList = em.createQuery("select distinct t from Team t join fetch t.memberList", Team.class)
                            .getResultList();

            for (Team t: teamList) {
                System.out.println("distinct team: " + t.getName() + "|memberss: " +
                        t.getMemberList().size());
                for (Member m : t.getMemberList()){
                    System.out.println("distinct member: " + m.getUsername());
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
