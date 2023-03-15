package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/*
    전략 패턴적용하기 테스트
     - 기존 템플릿메소드 패턴은 부모와 자식간에 상속으로 인해 자식이 부모의 어떠한 로직이 들어간 코드를 쓰던 않쓰던 강결합 되어 부모에서 코드수정시 자식에게도 영향을 미치게된다.
     - 스프링에서 bean 주입방식에 사용되는 디자인 패턴이 이 전략 패턴이다.
* */
@Slf4j
public class ContextV1Test {

    @Test
    void strategyV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}" , resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}" , resultTime);
    }

    /*
        템플릿 메소드 패턴 적용
     */
    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic1();
        template2.execute();
    }

    //익명 내부 클래스를 이용하여 클래스를 따로 생성하지 않고 하는 방법
    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {

            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        log.info("클래스 이름1={}",template1.getClass());
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {

            @Override
            protected void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        log.info("클래스 이름2={}",template2.getClass());
        template2.execute();
    }
    /*
        전략 패턴 사용한 테스트 코드
     */
    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1); //특정 전략 주입
        context1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2); //특정 전략 주입
        context2.execute();
    }
}
