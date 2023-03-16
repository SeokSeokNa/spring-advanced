package hello.advanced.trace.strategy.code.template;

import lombok.extern.slf4j.Slf4j;

/*전략 패턴에서 context 와 같은 역활
  스프링에서 보통 jdbcTemplate , RestTemplate 같은 인터페이스를 많이 봤을건데 이러한 인터페이스의 방식이 모두 "템플릿 콜백 패턴" 으로 구현 되어있다.
  템플릿 콜백 패턴은 디자인 패턴은 아니고 스프링에서 부르는 패턴방식이다.

  ContextV2 클래스가 템플릿 콜백 패턴을 이용한 방식이라고 보면된다.
* */
@Slf4j
public class TimeLogTemplate {

    public void execute(CallBack callBack) {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        callBack.call();
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}" , resultTime);
    }
}
