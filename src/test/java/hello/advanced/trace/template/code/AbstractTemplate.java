package hello.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/*
    템플릿 메소드 패턴 테스트해보기
     - 템플릿 이란 jsp thymeleaf를 예로들어보면 되는데 기반이되는 틀이라고 생각하면된다 , 저런 페이지를 만들때 기반안에서 조금조금씩 바꿔가며 만들기에 기반 틀이 있다고 생각하면된다.
       템플릿 메소드 패턴도 저 개념을 적용하여 변하지 않는 부분을 틀로 잡고 변하는 부분을 별도로 호출해서 해결하는 디자인 패턴이다.
       상속개념을 넣어 부모클래스에는 변하지 않는 틀 부분을 구성하고 변하는 부분은 자식클래스에 적용한다.
 */
public abstract class AbstractTemplate {

    public void execute() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        call(); //자식 클래스가 해당 메소드를 구현하여 중간에서 실행 될 수 있게함
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}" , resultTime);
    }

    //비즈니스 로직에 대한 추상 메소드
    protected abstract void call();
}
