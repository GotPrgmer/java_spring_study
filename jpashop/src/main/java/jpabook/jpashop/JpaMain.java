package jpabook.jpashop;

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
        try{

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
