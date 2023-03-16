package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.*;
import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


/*
    전략을 파라미터 전달받아 처리하는 전략패턴
     - 이전 필드주입 방식은 context를 싱글톤으로 사용하게되면 동시성를 고려해야 해서 전략을 미리 조립해두고 이후에는 자유롭게 변경하기 힘들었는데
        파라미터 방식을 사용하면 언제든지 필요에 따라 자유롭게 변경 가능해진다.
     - 단점으로는 실행할 떄 마다 전략을 계속 지정해주어야 한다는 점
 */
@Slf4j
public class ContextV2Test {


    @Test
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    //익명 내부 클래스 사용
    @Test
    void strategyV2() {
        ContextV2 context = new ContextV2();
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
    }

    //람다 방식으로 간소화
    @Test
    void strategyV3() {
        ContextV2 context = new ContextV2();
        context.execute(() -> log.info("비즈니스 로직1 실행"));
        context.execute(() -> log.info("비즈니스 로직2 실행"));
    }
}
