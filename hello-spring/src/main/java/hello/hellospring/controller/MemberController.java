package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//스프링처음에 뜰때(실행시킬때) 컨테이너 통이 생기면
//MemberController객체 생성해서 컨테이너 통에 넣어둠
@Controller
public class MemberController {

    private final  MemberService memberService;

    //세터 주입은 나중에 해당 함수로 변경할 수 있기 때문에 권장하지 않음
//    @Autowired
//    public void setMemberService(MemberService memberService){
//        this.memberService = memberService;
//    }

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

}
