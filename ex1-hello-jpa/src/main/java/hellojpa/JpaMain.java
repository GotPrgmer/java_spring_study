package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        //트랜잭션 얻기
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //멤버 저장
//        try{
        Member member = new Member();

//
//
//        Member member = new Member();
//
//        member.setId(1L);
//        member.setName("HelloA");
//        em.persist(member);
//        //트랜잭션 커밋
//        tx.commit();
//        } catch(Exception e){
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        try{
//
//        Member member = new Member();
//
//        member.setId(1L);
//        member.setName("HelloA");
//        em.persist(member);
//        //트랜잭션 커밋
//        tx.commit();
//        } catch(Exception e){
//            tx.rollback();
//        } finally {
//            em.close();
//        }
        try{


//            Member member = new Member(200L, "member200");
//            em.persist(member);
//
//            em.flush();
//            System.out.println("=================================");

/*            Member member = em.find(Member.class, 150L);

            em.clear();

            Member member2 = em.find(Member.class, 150L);*/

//            em.detach(member);
//            if(member.getName().equals("ZZZZZZ")){{
//                em.persist(member);
//
//            }}
//            System.out.println(member);

//            //비영속
//            Member member = new Member();
//            member.setId(100L);
//            member.setName("HelloJPA");

            //영속
//            em.persist(member);

//            Member findMember = em.find(Member.class, 101L);
//            Member findMember = em.find(Member.class, 102L);

//            System.out.println(findMember.getId());
//            System.out.println(findMember.getName());


//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2);
//            System.out.println("=======================================");



//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");
//            List<Member> result = em.createQuery("select m from Member as m ",Member.class)
//                    .getResultList();
//
//            for (Member member : result){
//                System.out.println("member.name = ");
//            }

//            em.remove(findMember);
            //트랜잭션 커밋
            tx.commit();
        } catch(Exception e){
            tx.rollback();
        } finally {
            em.close();
        }



        //팩토리까지 닫아줌
        emf.close();
    }
}
