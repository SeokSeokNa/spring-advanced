package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/*
 * 필드에 전략을 보관하는 방식
 * context는 strategy 인터페이스를 의존함(strategy 를 주입받음) -> strategy 구현체를 변경하거나 새로 만들어도 context 코드에는 영향을 미치지 않게된다, 쉽게 말해 원하는 전략을 언제든지 끼워넣어 바꿔도 된다는
 * context는 변하지 않는 로직을 같는 템플릿 역할을 하는 코드가 됨
 * 전략 변경시 context 안에 strategy를 통해 변경한다.
 *
 */
@Slf4j
public class ContextV1 {

    private Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        strategy.call(); //템플릿 메소드 패턴과 달리 부모가 변경시 자식이 영향을 받지 않게하기 위해 다른 상속 클래스에게 책임 위임함
                         //ContextV1 클래스가 와 call을 호출하는 자식 클래스들간의 관계를 끊어 변경하여도 영향을 받지 않음
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}" , resultTime);
    }
}
