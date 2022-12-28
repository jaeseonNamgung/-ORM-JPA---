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
            Team team = new Team();
            team.setName("팀A");


            Member member = new Member();
            member.setUsername("member1");
            member.setAge(26);
            member.changeTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            // 기본 CASE 식
            String query = "select " +
                    "case when m.age <= 10 then '학생요금' " +
                    "when m.age >= 60 then '경로요금' " +
                    "else '일반요금' " +
                    "end " +
                    "from Member m";

            List<String> memberList = em.createQuery(query, String.class)
                            .getResultList();
            for (String value: memberList) {
                System.out.println("result: " + value);
            }

            // 단순 CASE 식
            String query2 = "select " +
                    "case t.name " +
                    "when '팀A' then '인세티브110%' " +
                    "when '팀B' then '인제티브120%' " +
                    "else '인세티브105%' " +
                    "end " +
                    "from Team t";

            List<String> teamList = em.createQuery(query2, String.class)
                    .getResultList();
            for (String value: teamList) {
                System.out.println("teamResult: " + value);
            }

            // COALESCE: 하나씩 조회해서 null이 아니면 반환 값을 반환 null 이면 coalesce에 지정한 값을 반환
            Member member2 = new Member();
            Team team2 = new Team();
            member2.setUsername(null);
            member2.changeTeam(team2);
            em.persist(member2);
            String query3 = "select coalesce(m.username, '이름 없는 회원') from Member m";
            List<String> result3 = em.createQuery(query3, String.class)
                            .getResultList();
            for (String value: result3) {
                System.out.println("COALESCE: " + value);
            }

            // NULLIF: 두 값이 같으면 null 반환, 다르면 첫번 째 값 반환
            // 사용자 이름이 관리자거나 null이면 null을 반환하고 나머지는 본인의 이름을 봔환
            Team team3 = new Team();
            Member member3 = new Member();
            member3.setUsername("관리자");
            member3.changeTeam(team3);
            em.persist(member3);
            String query4 = "select NULLIF(m.username, '관리자') from Member m where m.username is not null";
            List<String> result4 = em.createQuery(query4, String.class)
                            .getResultList();
            for (String value: result4) {
                System.out.println("NULLIF: " + value);
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
