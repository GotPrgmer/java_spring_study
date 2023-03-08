package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>, MemberRepository {
    //JPQL select m from Member m where m.name = ? 메서드 네임으로 쿼리가 짜짐
    @Override
    Optional<Member> findByName(String name);
}
