package hellojpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setAge(0);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member1.setAge(0);
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member1.setAge(0);
            member3.setTeam(teamB);
            em.persist(member3);


            em.flush();
            em.clear();

            /**
             * 벌크성 쿼리
             */
            //FLUSH 자동 호출
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            System.out.println("resultCount = " + resultCount);

            em.clear(); //영속성 컨텍스트 초기화 필수!
            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge());


            System.out.println("member1.getAge() = " + member1.getAge());
            System.out.println("member2.getAge() = " + member2.getAge());
            System.out.println("member3.getAge() = " + member3.getAge());
            /**
             * namedQuery
             */
//            List<Member> result = em.createNamedQuery("Member.findByUsername", Member.class)
//                    .setParameter("username", "회원1")
//                    .getResultList();

            /**
             * fetch join
             */
//            String query = "select t from Team t join fetch t.members";

//            String query = "select 'a' || 'b' from Member m"; // ==concat

//            String query = "select locate('de', 'abcdefg') from Member m"; //locate: 반환 타입 = Integer

//            String query = "select size(t.members) from Team t"; //size: 콜렉션 size

//            String query = "select group_concat(m.username) from Member m"; //사용자 정의 함수 호출


            /**
             * nullif: username = '관리자'이면 null 반환
             */
//            String query = "select nullif(m.username, '관리자') from Member m";

            /**
             * coalesce: username = null이면 '이름 없는 회원' 반환
             */
//            String query = "select coalesce(m.username, '이름 없는 회원') from Member m";

            /**
             * 기본 CASE 식
             */
//            String query =
//                    "select " +
//                            "case when m.age <= 10 then '학생요금' " +
//                            "when m.age >= 60 then '경로요금' " +
//                            "else '일반요금' " +
//                            "end " +
//                            "from Member m";

//            List<Team> result = em.createQuery(query, Team.class)
//                    .getResultList();


            /**
             * enum 값을 이용한 where
             */
//            String query = "select m.username, 'HELLO', TRUE from Member m " +
//                    "where m.type = :userType";
//            List<Object[]> result = em.createQuery(query)
//                    .setParameter("userType", MemberType.ADMIN)
//                    .getResultList();
//
//            for (Object[] objects : result) {
//                System.out.println("objects = " + objects[0]);
//                System.out.println("objects = " + objects[1]);
//                System.out.println("objects = " + objects[2]);
//            }
            /**
             * 조인
             */
//            String query = "select m from Member m left join m.team t";
//            String query = "select m from Member m, Team t where m.username = t.name"; //세타 조인(막 조인)
//            String query = "select m from Member m left join m.team t on t.name='A'";  //조인 대상 필터링
//            String query = "select m from Member m left join Team t on m.username = t.name";  //연관 관계 없는 엔티티 외부 조인
//            List<Member> result = em.createQuery(query, Member.class)
//                    .getResultList();

            /**
             * 페이징 API
             */
//            List<Member> result = em.createQuery("select m from Member m ORDER BY m.age desc", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//            System.out.println("result.size = " + result.size());
//            for (Member member1 : result) {
//                System.out.println("member1 = " + member1);
//            }
            /**
             * 프로젝션 - 여러 값 조회
             */
//            List<MemberDTO> result = em.createQuery("select new hellojpql.MemberDTO( m.username, m.age) from Member m", MemberDTO.class)
//                    .getResultList();
//
//            MemberDTO memberDTO = result.get(0);
//            System.out.println("memberDTO = " + memberDTO.getUsername());
//            System.out.println("memberDTO = " + memberDTO.getAge());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
