package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/*
 * 전략을 파라미터로 전달 받는 방식
 */
@Slf4j
public class ContextV2 {

    public void execute(Strategy strategy) {
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
