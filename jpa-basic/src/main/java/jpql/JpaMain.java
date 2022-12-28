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
            team.setName("team");

            Member member = new Member();
            member.setAge(26);
            member.setUsername("member");
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();


            // CONCAT(str, 이어 붙일 문자열) : 문자열 이어 붙이기
            List<String> result = em.createQuery("select concat(m.username, ' userName') from Member m", String.class)
                    .getResultList();
            for (String value: result) {
                System.out.println("concat: " + value);
            }

            // SUBSTRING(str, pos, len) : str 문자열을 pos 위치에서 (0이 아닌 1부터 시작) len 길이 만큼 문자열은 자른다.
            result = em.createQuery("select substring(m.username, 2, 4) from Member m", String.class)
                            .getResultList();
            for (String value: result) {
                System.out.println("SUBSTRING: " + value);
            }
            result = em.createQuery("select trim('     문자열 공백 제거     ') from Member m", String.class)
                            .getResultList();

            // TRIM(str): 문자열 시작과 끝 부분 공백 제거
            for (String value: result) {
                System.out.println("TRIM: " + value);
            }

            // LOWER(str): 소문자로 변경, UPPER(str): 대문자로 변경
            String str = em.createQuery("select lower('ABCDE') from Member m", String.class)
                    .getSingleResult();
            System.out.println("LOWER: " + str);
            str = em.createQuery("select UPPER('abcde') from Member m", String.class)
                    .getSingleResult();
            System.out.println("UPPER: " + str);
            // LENGTH(str) : 문자열 길이
            Integer init = em.createQuery("select length('abcde') from Member m", Integer.class)
                    .getSingleResult();
            System.out.println("LENGTH: " + init);

            // LOCATE(substr, str, [pos]): str 에 있는 문자열 substr 의 검색위치를 정수로 반환하는데, substr 이 str 에 없으면 0을 반환
            init = em.createQuery("select LOCATE('jae', 'namgungjaeseon') from Member m", Integer.class)
                    .getSingleResult();
            System.out.println("LOCATE: " + init);

            // ABS(숫자)`: 절댓값을 반환하는 함수,
            // SQRT(숫자): 제곱근을 반환하는 함수,
            // MOD(분자 분모): 분자를 분모를 나눈 나머지를 구한다.
            // SIZE(컬렉션): 컬렉션의 크기를 구하는 함수
            init = em.createQuery("select size(t.memberList) from Team t", Integer.class)
                    .getSingleResult();
            System.out.println("SIZE: " + init);
            // INDEX(별칭): LIST 타입 컬렉션의 위치값을 구하는 함수이며 JPA에서 쓰인다


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
