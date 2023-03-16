package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.Event;


//CRUD 함수를 JpaRepository가 들고 있음.
//@Repository라는 어노테이션이 없어도 IoC됨. 이유는 JpaRepository를 상속했기 때문이다.
public interface UserRepository extends JpaRepository <User,Integer> { // 모델은 User pk는 Integer라서
    //findBy규칙 -> Username문법 (이렇게 함수를 사용하면 저절로 쿼리를 작성해준다.)
    // select * from where username = ? 이 호출됨. (물음표에는 username이 들어온다.)
    public User findByUsername(String username); // Jpa

    // select * from user where email = ? 실행(jpq Query methods)
//    public User findByEmail();
}
